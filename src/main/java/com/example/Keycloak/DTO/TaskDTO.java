package com.example.Keycloak.DTO;

import com.example.Keycloak.model.Status;

public record TaskDTO(Long id,
                      String title,
                      String description,
                      Status status,
                      Long assigneeId,
                      Long projectId) {
}
