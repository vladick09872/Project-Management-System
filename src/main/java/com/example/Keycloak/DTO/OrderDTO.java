package com.example.Keycloak.DTO;

import com.example.Keycloak.model.OrderStatus;

import java.time.LocalDate;

public record OrderDTO(Long id,
                       Long userId,
                       LocalDate orderDate,
                       String deliveryAddress,
                       OrderStatus status) {
}
