package com.example.Keycloak.DTO;

import com.example.Keycloak.model.Role;

public record UserDTO(Long id,
                      String username,
                      String email,
                      Role role) {
}
