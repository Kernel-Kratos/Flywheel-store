package com.shoppingbackend.flywheel_store.service.cartItem;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.shoppingbackend.flywheel_store.exceptions.ResourceNotFoundException;
import com.shoppingbackend.flywheel_store.model.Cart;
import com.shoppingbackend.flywheel_store.model.CartItem;
import com.shoppingbackend.flywheel_store.model.Product;
import com.shoppingbackend.flywheel_store.repository.CartItemRepository;
import com.shoppingbackend.flywheel_store.repository.CartRepository;
import com.shoppingbackend.flywheel_store.service.cart.ICartService;
import com.shoppingbackend.flywheel_store.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final ICartService cartService;

    @Override
    public void addItemtoCart(Long cartId, Long productId, int quantity) {
        /*1. get cardId
          2. Get productId
          3. Check if product is in the cart
          4. if yes then increase the product acc to request
          5. if no then create new cartItem entry  */
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem());
        if(cartItem.getId() ==  null){
        cartItem =  new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setUnitPrice(product.getPrice());
        }
        else{
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
       Cart cart = cartService.getCart(cartId);
       CartItem itemToRemove = getCartItem(cartId, productId);
       cart.removeItem(itemToRemove);
       cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getItems()
                    .stream().map(CartItem :: getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart); 
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId){
        Cart cart = cartService.getCart(cartId);
        return cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().
                orElseThrow(() ->  new ResourceNotFoundException("Product not found"));
    }

}
