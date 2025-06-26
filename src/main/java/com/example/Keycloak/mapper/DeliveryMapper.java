package com.example.Keycloak.mapper;

import com.example.Keycloak.DTO.DeliveryDTO;
import com.example.Keycloak.model.Delivery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    DeliveryDTO toDTO(Delivery delivery);

    Delivery toEntity(DeliveryDTO deliveryDTO);

}
