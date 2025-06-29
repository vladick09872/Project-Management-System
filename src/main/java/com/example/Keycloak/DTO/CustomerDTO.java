package com.example.Keycloak.DTO;

public record CustomerDTO(Long id,
                          String name,
                          String email,
                          String phone,
                          Long purchaseId,
                          Long testDriveRequestId) {
}
