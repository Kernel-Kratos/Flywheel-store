package com.shoppingbackend.flywheel_store.service.cart;

import java.math.BigDecimal;
import java.util.List;

import com.shoppingbackend.flywheel_store.dto.CartDto;
import com.shoppingbackend.flywheel_store.model.Cart;
import com.shoppingbackend.flywheel_store.model.User;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Cart intializeNewCart(User user);
    CartDto convertToCartDto(Cart cart);
    List<CartDto> getConvertedCart(List<Cart> carts);
    Cart getCartByUserId(Long userId);
}
