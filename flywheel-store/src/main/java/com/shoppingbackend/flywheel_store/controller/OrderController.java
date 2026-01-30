package com.shoppingbackend.flywheel_store.controller;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingbackend.flywheel_store.dto.OrderDto;
import com.shoppingbackend.flywheel_store.enums.PaymentStatus;
import com.shoppingbackend.flywheel_store.exceptions.ResourceNotFoundException;
import com.shoppingbackend.flywheel_store.model.Order;
import com.shoppingbackend.flywheel_store.model.Payment;
import com.shoppingbackend.flywheel_store.model.User;
import com.shoppingbackend.flywheel_store.repository.UserRepository;
import com.shoppingbackend.flywheel_store.request.CreatePaymentRequest;
import com.shoppingbackend.flywheel_store.response.ApiResponse;
import com.shoppingbackend.flywheel_store.service.order.IOrderService;
import com.shoppingbackend.flywheel_store.service.payment.IPaymentService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;
    private final UserRepository userRepository;
    private final IPaymentService paymentService;

    @PostMapping("/order/placeOrder")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId){
        try{
            Order order = orderService.placeOrder(userId);
            OrderDto convertedOrder = orderService.convertToDto(order);

            CreatePaymentRequest request = request(userId, order);
            Payment payment = paymentService.createPayment(request);
            convertedOrder.setPaymentUrl(payment.getPaymentUrl());
            paymentService.TrackPayment(payment.getId());
            return ResponseEntity.ok(new ApiResponse("Order Placed", convertedOrder));
            /*Payment payment = paymentService.createPayment(request);
            convertedOrder.setPaymentUrl(payment.getPaymentUrl());
            paymentService.TrackPayment(payment.getId());
            return ResponseEntity.ok(new ApiResponse("Order Placed", convertedOrder));
            Subtle bug:- Introduction of deadlock.
            1. User starts payment.
            2. ConvertedOrder gets Payment Url but responseEntity is not returned yet
            3. TrackPayment is called making the server wait
            4. This means the connection is open but user doesn't have the url. They will have that after 17mins are over but connection is closed */
        }  catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("User not found ", null));
        } 
        catch(OptimisticEntityLockException e){
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException ("User Not found") );
            Payment payment = paymentService.getPaymentByUser(user)
                    .stream()
                    .filter(payemnt -> payemnt.getPaymentStatus().equals(PaymentStatus.IN_PROGRESS)).collect(null);
            payment.setPaymentStatus(PaymentStatus.FAILED);
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("Product's Price or Inventory has been changed. Please Refresh", null));
        }
    }

    private CreatePaymentRequest request(Long userId, Order order){
        User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        LocalDateTime dateTime = order.getOrderDateTIme();
        PaymentStatus paymentStatus = PaymentStatus.IN_PROGRESS;

        CreatePaymentRequest request = new CreatePaymentRequest(paymentStatus, dateTime, order, user);
        return request;
    }
    

    @GetMapping("/order/getOrder/orderId/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        try {
            Order order = orderService.getOrder(orderId);
            OrderDto convertedOrder = orderService.convertToDto(order);
            return ResponseEntity.ok(new ApiResponse("Order found", convertedOrder));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No order found with the id " + orderId, orderId));
        }
    }

    @GetMapping("/order/getOrder/userId/{userId}")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        try {
            List<OrderDto> order = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Order found", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No order found with the id " + userId, userId));
        } 
    }
    


}
