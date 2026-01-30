package com.shoppingbackend.flywheel_store.model;

import java.time.Duration;
import java.time.LocalDateTime;

import com.shoppingbackend.flywheel_store.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String paymentUrl;
    private PaymentStatus paymentStatus;
    private LocalDateTime dateTimeStart;
    private LocalDateTime dateTimeEnd;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private final Duration timeOutDuration = Duration.ofSeconds(1020);

    public void setEndTime(){
        this.dateTimeEnd = this.dateTimeEnd.plus(timeOutDuration);
    }
}
