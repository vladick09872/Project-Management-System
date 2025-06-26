package com.example.Keycloak.mapper;

import com.example.Keycloak.DTO.OrderDTO;
import com.example.Keycloak.DTO.OrderItemDTO;
import com.example.Keycloak.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemDTO toDTO(OrderItem orderItem);

    OrderDTO toEntity(OrderItemDTO orderItemDTO);
}
