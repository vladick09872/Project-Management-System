package com.example.Keycloak.service;

import com.example.Keycloak.DTO.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long productId);

    ProductDTO updateProduct(Long productId, int deltaQuantity);

    ProductDTO createProduct(ProductDTO productDTO);

    void deleteProduct(Long productId);
}
