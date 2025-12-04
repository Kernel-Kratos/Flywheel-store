package com.shoppingbackend.flywheel_store.dto;

import java.math.BigDecimal;
import java.util.Set;

import lombok.Data;

@Data
public class CartDto {
    private Long id;
    private Set<CartItemDto> items;
    private BigDecimal totalAmount;
}
