package com.example.Keycloak.service;

import com.example.Keycloak.DTO.OrderItemDTO;

import java.util.List;

public interface OrderItemService {
    List<OrderItemDTO> getItemsByOrderId(Long orderId);
}
