package com.example.Keycloak.DTO;

import com.example.Keycloak.model.DeliveryStatus;

import java.time.LocalDate;

public record DeliveryDTO(Long id,
                          Long orderId,
                          Long courierId,
                          LocalDate deliveryDate,
                          DeliveryStatus status) {
}
