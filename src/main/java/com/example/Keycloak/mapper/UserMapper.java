package com.example.Keycloak.mapper;

import com.example.Keycloak.DTO.UserDTO;
import com.example.Keycloak.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);

    User toModel(UserDTO userDTO);
}
