package com.shoppingbackend.flywheel_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingbackend.flywheel_store.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

    void deleteAllByCartId(Long id);

    CartItem findByProductId(Long productId);


    
} 