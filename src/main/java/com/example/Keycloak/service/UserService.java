package com.example.Keycloak.service;

import com.example.Keycloak.DTO.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getCurrentUser();

    List<UserDTO> getAllCouriers();
}
