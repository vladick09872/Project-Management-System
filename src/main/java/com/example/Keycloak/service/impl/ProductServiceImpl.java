package com.example.Keycloak.service.impl;

import com.example.Keycloak.DTO.ProductDTO;
import com.example.Keycloak.mapper.ProductMapper;
import com.example.Keycloak.model.Product;
import com.example.Keycloak.repository.ProductRepository;
import com.example.Keycloak.service.ProductService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found."));

        return productMapper.toDTO(product);
    }

    @Override
    public ProductDTO updateProduct(Long productId, int deltaQuantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found."));

        product.setQuantityInStock(product.getQuantityInStock() + deltaQuantity);

        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        return productMapper.toDTO(productRepository.save(productMapper.toEntity(productDTO)));
    }

    @Override
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new NotFoundException("Product not found.");
        }

        productRepository.deleteById(productId);
    }
}
