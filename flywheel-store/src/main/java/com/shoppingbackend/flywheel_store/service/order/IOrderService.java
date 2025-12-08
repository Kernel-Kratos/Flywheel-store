package com.shoppingbackend.flywheel_store.service.order;

import java.util.List;

import com.shoppingbackend.flywheel_store.dto.OrderDto;
import com.shoppingbackend.flywheel_store.model.Order;

public interface IOrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
    OrderDto convertToDto(Order order);
}
