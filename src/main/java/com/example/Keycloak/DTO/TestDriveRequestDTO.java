package com.example.Keycloak.DTO;

import com.example.Keycloak.model.Status;

import java.time.LocalDate;

public record TestDriveRequestDTO(Long id,
                                  LocalDate requestDate,
                                  LocalDate preferredDate,
                                  Status status,
                                  Long carId,
                                  Long customerId) {
}
