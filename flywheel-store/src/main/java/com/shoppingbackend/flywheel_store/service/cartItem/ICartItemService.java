package com.shoppingbackend.flywheel_store.service.cartItem;

import com.shoppingbackend.flywheel_store.model.CartItem;

public interface ICartItemService {
    void addItemtoCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);
    CartItem getCartItem(Long cartId, Long productId);
}
