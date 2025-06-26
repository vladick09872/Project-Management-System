package com.example.Keycloak.mapper;

import com.example.Keycloak.DTO.OrderDTO;
import com.example.Keycloak.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toDTO(Order order);

    Order toEntity(OrderDTO orderDTO);
}
