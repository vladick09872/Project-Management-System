package com.example.Keycloak.DTO;

public record ProjectDTO(Long id,
                         String name,
                         String description,
                         Long taskId,
                         Long ownerId) {
}
