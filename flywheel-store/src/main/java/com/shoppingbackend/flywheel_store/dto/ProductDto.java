package com.shoppingbackend.flywheel_store.dto;

import java.math.BigDecimal;
import java.util.List;


import com.shoppingbackend.flywheel_store.model.Category;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    
    private Category category;// maintains the bidirectional relation. When a product is loaded it category can be retrived easily.

    private List<ImageDto> images;
}
