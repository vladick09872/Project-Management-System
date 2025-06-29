package com.example.Keycloak.DTO;

import java.time.LocalDate;

public record PurchaseDTO(Long id,
                          LocalDate purchaseDate,
                          Double priceAtPurchase,
                          Long carId,
                          Long customerId) {
}
