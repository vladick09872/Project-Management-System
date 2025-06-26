package com.example.Keycloak.controller;

import com.example.Keycloak.DTO.ProductDTO;
import com.example.Keycloak.DTO.UserDTO;
import com.example.Keycloak.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Get products", description = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/all")
    @PreAuthorize("hasRole('COURIER')")
    public List<ProductDTO> getAll() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Get product by ID", description = "Get current product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{productId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'COURIER')")
    public ProductDTO getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @Operation(summary = "Update product", description = "Update current product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping("/update/{productId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ProductDTO update(@PathVariable Long productId, @RequestParam int deltaQuantity) {
        return productService.updateProduct(productId, deltaQuantity);
    }

    @Operation(summary = "Create product", description = "Add new product to system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping("/create")
    @PreAuthorize("hasRole('MANAGER')")
    public ProductDTO create(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @Operation(summary = "Delete current product", description = "Delete product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @DeleteMapping("/delete/{productId}")
    @PreAuthorize("hasRole('MANAGER')")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}
