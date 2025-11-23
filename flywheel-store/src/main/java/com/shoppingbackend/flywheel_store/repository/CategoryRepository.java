package com.shoppingbackend.flywheel_store.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingbackend.flywheel_store.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

    boolean existsByName(String name);
    
}
