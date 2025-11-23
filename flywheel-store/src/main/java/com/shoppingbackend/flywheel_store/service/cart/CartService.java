package com.shoppingbackend.flywheel_store.service.cart;

import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.shoppingbackend.flywheel_store.dto.CartDto;
import com.shoppingbackend.flywheel_store.exceptions.ResourceNotFoundException;
import com.shoppingbackend.flywheel_store.model.Cart;

import com.shoppingbackend.flywheel_store.repository.CartRepository;

import jakarta.transaction.Transactional;

import com.shoppingbackend.flywheel_store.repository.CartItemRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;
    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        //Line 22 and 23 refresh the total amount based on current items in the cart 
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void clearCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return cart.getTotalAmount();
    }

    @Override
    public Long intializeNewCart(){
        Cart newCart = new Cart();
       // Long newCartId = cartIdGenerator.incrementAndGet();
       // newCart.setId(newCartId);
       
        Cart savedCart = cartRepository.save(newCart);
        return savedCart.getId();
    }

    @Override
    public List<CartDto> getConvertedCart(List <Cart> carts){
        return carts.stream().map(this::convertToCartDto).toList();
    }
    @Override
    public CartDto convertToCartDto(Cart cart){
        CartDto cartDto = modelMapper.map(cart, CartDto.class);
        return cartDto;
    }
}
