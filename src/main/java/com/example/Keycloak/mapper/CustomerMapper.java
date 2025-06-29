package com.example.Keycloak.mapper;

import com.example.Keycloak.DTO.CustomerDTO;
import com.example.Keycloak.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO toDTO(Customer customer);

    Customer toModel(CustomerDTO customerDTO);
}
