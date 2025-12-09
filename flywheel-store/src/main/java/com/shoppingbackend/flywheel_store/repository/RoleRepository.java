package com.shoppingbackend.flywheel_store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingbackend.flywheel_store.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String role);

}
