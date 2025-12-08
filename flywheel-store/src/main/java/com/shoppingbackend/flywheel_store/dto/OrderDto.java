package com.shoppingbackend.flywheel_store.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private LocalDateTime orderDateTime;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemDto> items;
}
