package com.shoppingbackend.flywheel_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingbackend.flywheel_store.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByProductId(Long id);
}
