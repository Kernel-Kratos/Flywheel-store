package com.shoppingbackend.flywheel_store.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;   
    private String email;

    private List<OrderDto> orders = new ArrayList<>();
    private CartDto cart;

}   
