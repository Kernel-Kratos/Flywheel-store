package com.shoppingbackend.flywheel_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingbackend.flywheel_store.model.User;

public interface UserRepository extends JpaRepository <User, Long>{

    boolean existsByEmail(String email);

    User findByEmail(String email);

}
