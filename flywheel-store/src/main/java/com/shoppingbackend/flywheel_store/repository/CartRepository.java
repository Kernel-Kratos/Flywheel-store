package com.shoppingbackend.flywheel_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingbackend.flywheel_store.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

}
