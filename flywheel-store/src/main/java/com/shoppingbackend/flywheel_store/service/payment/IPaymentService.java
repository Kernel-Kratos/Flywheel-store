package com.shoppingbackend.flywheel_store.service.payment;

import java.util.List;

import com.shoppingbackend.flywheel_store.enums.PaymentStatus;
import com.shoppingbackend.flywheel_store.model.Payment;
import com.shoppingbackend.flywheel_store.model.User;
import com.shoppingbackend.flywheel_store.request.CreatePaymentRequest;

public interface IPaymentService {
    Payment getPaymentById(Long id);
    PaymentStatus getPaymentStatusById(Long id);
    List<Payment> getPaymentByUser(User user);
    Payment createPayment(CreatePaymentRequest request);
    void doPayment(Long id);
    void TrackPayment(Long id);
}
