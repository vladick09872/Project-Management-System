package com.example.Keycloak.service.impl;

import com.example.Keycloak.DTO.UserDTO;
import com.example.Keycloak.mapper.UserMapper;
import com.example.Keycloak.model.Role;
import com.example.Keycloak.model.User;
import com.example.Keycloak.repository.UserRepository;
import com.example.Keycloak.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new SecurityException("No authentication user.");
        }

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow();

        return userMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getAllCouriers() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole().equals(Role.COURIER))
                .map(userMapper::toDTO)
                .toList();
    }
}
