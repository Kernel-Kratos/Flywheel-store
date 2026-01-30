package com.shoppingbackend.flywheel_store.service.payment;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.shoppingbackend.flywheel_store.enums.PaymentStatus;
import com.shoppingbackend.flywheel_store.exceptions.ResourceNotFoundException;
import com.shoppingbackend.flywheel_store.model.Payment;
import com.shoppingbackend.flywheel_store.model.User;
import com.shoppingbackend.flywheel_store.repository.PaymentRepository;
import com.shoppingbackend.flywheel_store.request.CreatePaymentRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaymentService implements IPaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Payment Couldn't be found"));
         
    }

    @Override
    public PaymentStatus getPaymentStatusById(Long id) {
        Payment payment = getPaymentById(id);
        return payment.getPaymentStatus();
    }

    @Override
    public List<Payment> getPaymentByUser(User user) {
        List<Payment> payments = paymentRepository.findAllByUser(user);
        return payments;
    }

    @Override
    public Payment createPayment(CreatePaymentRequest request) {
        return Optional.of(request)
                .map(req -> {
                    Payment payment = new Payment();
                    payment.setDateTimeStart(request.getDateTime());
                    payment.setOrder(request.getOrder());
                    payment.setPaymentStatus(request.getPaymentStatus());
                    payment.setUser(request.getUser());

                    String buildPaymentUrl = "api/v1/payments/payment/Url/";
                    String paymentUrl = buildPaymentUrl + payment.getId();

                    payment.setPaymentUrl(paymentUrl);
                    return paymentRepository.save(payment);
                }).orElseThrow(() -> new RuntimeException("There was an error while creating"));
    }

    //Post request but don't do anything.
    @Override
    public void doPayment(Long id){
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could load the payment"));
        payment.setPaymentStatus(PaymentStatus.SUCCESSFUL);
    }

    @Override
    @Async
    public void TrackPayment(Long id){
        Payment payment = getPaymentById(id);
        Long timeout = timeCalculation(id);
       while(timeout >= 0){
        if(payment.getPaymentStatus() == PaymentStatus.SUCCESSFUL){
            break;
        }
        timeout = timeCalculation(id);
        }
        if(timeout <= 0){
            payment.setPaymentStatus(PaymentStatus.FAILED);
            throw new RuntimeException("Payment Failed");
        }
    }
    private long timeCalculation (Long id){
        Payment payment = getPaymentById(id);
        long timeEndSeconds = payment.getDateTimeEnd().toEpochSecond(ZoneOffset.UTC);
        long milliseconds = System.currentTimeMillis();
        long seconds = milliseconds / 1000;
        Long difference = timeEndSeconds - seconds;
        return difference;
    }

}
