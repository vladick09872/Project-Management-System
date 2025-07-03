package com.example.Keycloak.service.impl;

import com.example.Keycloak.DTO.UserDTO;
import com.example.Keycloak.mapper.UserMapper;
import com.example.Keycloak.model.User;
import com.example.Keycloak.repository.UserRepository;
import com.example.Keycloak.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        User user = userMapper.toModel(userDTO);

        User savedUser = userRepository.save(user);

        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(Long userId) {
       User user = userRepository.findById(userId)
               .orElseThrow();

       return userMapper.toDTO(user);
    }
}
