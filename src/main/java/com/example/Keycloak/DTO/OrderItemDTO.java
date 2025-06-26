package com.example.Keycloak.DTO;

public record OrderItemDTO(Long id,
                           Long productId,
                           int quantity,
                           Double priceAtPurchase,
                           Long orderId) {
}
