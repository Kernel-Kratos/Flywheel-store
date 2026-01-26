package com.shoppingbackend.flywheel_store;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shoppingbackend.flywheel_store.exceptions.ResourceNotFoundException;
import com.shoppingbackend.flywheel_store.model.Cart;
import com.shoppingbackend.flywheel_store.model.CartItem;
import com.shoppingbackend.flywheel_store.model.Product;
import com.shoppingbackend.flywheel_store.repository.ProductRepository;
import com.shoppingbackend.flywheel_store.service.cart.CartService;
import com.shoppingbackend.flywheel_store.service.order.OrderService;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CartService cartService;

    @InjectMocks
    private OrderService orderService;
    @Test
    void placeOrder_shouldFail_WhenNoStock (){
        Product outOfStockProduct = new Product();
        outOfStockProduct.setInventory(0);

        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setProduct(outOfStockProduct);
        cartItem.setQuantity(1);
        cart.setItems(Set.of(cartItem));
        
        when(cartService.getCartByUserId(1L)).thenReturn(cart);

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.placeOrder(1L);
        });
    }
}
