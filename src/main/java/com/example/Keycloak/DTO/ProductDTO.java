package com.example.Keycloak.DTO;

public record ProductDTO(Long id,
                         String name,
                         String description,
                         Double price,
                         int quantityInStock) {
}
