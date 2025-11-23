package com.shoppingbackend.flywheel_store.service.cart;

import java.math.BigDecimal;
import java.util.List;

import com.shoppingbackend.flywheel_store.dto.CartDto;
import com.shoppingbackend.flywheel_store.model.Cart;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Long intializeNewCart();
    CartDto convertToCartDto(Cart cart);
    List<CartDto> getConvertedCart(List<Cart> carts);
}
