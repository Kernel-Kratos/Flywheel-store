package com.shoppingbackend.flywheel_store.request;

import java.time.LocalDateTime;

import com.shoppingbackend.flywheel_store.enums.PaymentStatus;
import com.shoppingbackend.flywheel_store.model.Order;
import com.shoppingbackend.flywheel_store.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatePaymentRequest {
    private PaymentStatus paymentStatus;
    private LocalDateTime dateTime;
    private Order order;
    private User user;

    public CreatePaymentRequest (User user, Order order, LocalDateTime dateTime, PaymentStatus paymentStatus){
        this.order = order;
        this.user =  user;
        this.dateTime = dateTime;
        this.paymentStatus = paymentStatus;
    }
}
