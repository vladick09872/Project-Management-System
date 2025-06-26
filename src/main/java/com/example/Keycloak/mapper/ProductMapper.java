package com.example.Keycloak.mapper;

import com.example.Keycloak.DTO.ProductDTO;
import com.example.Keycloak.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO productDTO);
}
