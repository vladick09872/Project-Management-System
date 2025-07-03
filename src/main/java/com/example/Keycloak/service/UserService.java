package com.example.Keycloak.service;

import com.example.Keycloak.DTO.UserDTO;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);

    UserDTO getUserById(Long userId);
}
