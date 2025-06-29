package com.example.Keycloak.mapper;

import com.example.Keycloak.DTO.PurchaseDTO;
import com.example.Keycloak.model.Purchase;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {
    PurchaseDTO toDTO(Purchase purchase);

    Purchase toModel(PurchaseDTO purchaseDTO);
}
