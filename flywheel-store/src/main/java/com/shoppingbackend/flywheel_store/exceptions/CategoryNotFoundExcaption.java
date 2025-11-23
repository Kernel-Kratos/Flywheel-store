package com.shoppingbackend.flywheel_store.exceptions;

public class CategoryNotFoundExcaption extends RuntimeException {
    public CategoryNotFoundExcaption(String message){
        super(message);
    }
}
