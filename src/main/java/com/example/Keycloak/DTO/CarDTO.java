package com.example.Keycloak.DTO;

public record CarDTO(Long id,
                     String brand,
                     String model,
                     int year,
                     double price,
                     boolean inStock,
                     Long purchaseId,
                     Long testDriveRequestId) {
}
