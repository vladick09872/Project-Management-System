package com.example.Keycloak.DTO;

import com.example.Keycloak.model.USER_ROLE;

public record UserDTO(Long id,
                      String username,
                      String email,
                      USER_ROLE role,
                      Long taskId,
                      Long projectId) {
}
