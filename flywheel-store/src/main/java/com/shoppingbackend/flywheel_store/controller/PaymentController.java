package com.shoppingbackend.flywheel_store.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingbackend.flywheel_store.exceptions.ResourceNotFoundException;
import com.shoppingbackend.flywheel_store.response.ApiResponse;
import com.shoppingbackend.flywheel_store.service.payment.IPaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/payments")
public class PaymentController {
    private final IPaymentService paymentService;

    @GetMapping("/payment/getPayment/id/{id}")
    public ResponseEntity<ApiResponse> getPaymentById(@PathVariable Long id){
       try {
         return ResponseEntity.ok(new ApiResponse("Found", paymentService.getPaymentById(id)));
       } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not found", null));
       }
    }
    
    @GetMapping("/payment/status/id/{id}")
    public ResponseEntity<ApiResponse> getPaymentStatus(@PathVariable Long id){
        try {
            return ResponseEntity.ok(new ApiResponse("Found!!!", paymentService.getPaymentStatusById(id)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not found", null));
        }
    }

    @PostMapping("/payment/doPayment/{id}")
    public ResponseEntity<ApiResponse> doPayment(Long id){
        paymentService.doPayment(id);
        return ResponseEntity.ok(new ApiResponse("Payment successfull", null));
    }
}
