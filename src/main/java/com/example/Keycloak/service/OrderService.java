package com.example.Keycloak.service;

import com.example.Keycloak.DTO.OrderDTO;
import com.example.Keycloak.model.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO getOrdersByUser(String username);

    List<OrderDTO> getAllOrders();

    OrderDTO updateOrderStatus(Long orderId, OrderStatus newStatus);

    void cancelOrder(Long orderId, String username);
}
