package com.shoppingbackend.flywheel_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingbackend.flywheel_store.model.Payment;
import com.shoppingbackend.flywheel_store.model.User;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByUser(User user);

}
