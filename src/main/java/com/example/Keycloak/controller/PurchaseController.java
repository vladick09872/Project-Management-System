package com.example.Keycloak.controller;

import com.example.Keycloak.DTO.CarDTO;
import com.example.Keycloak.DTO.PurchaseDTO;
import com.example.Keycloak.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @Operation(summary = "Purchase car", description = "Purchases new car for customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = CarDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid data request",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping("/{carId}/{customerId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<PurchaseDTO> purchaseCar(@PathVariable Long carId, @PathVariable Long customerId) {
        return ResponseEntity.ok(purchaseService.purchaseCar(carId, customerId));
    }

    @Operation(summary = "Get purchases by ID", description = "Return current purchase by customer ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = CarDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid data request",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PurchaseDTO>> getPurchasesByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(purchaseService.getPurchasesByCustomerId(customerId));
    }

    @Operation(summary = "Get all purchase", description = "Return all purchases")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = CarDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid data request",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PurchaseDTO>> getAllPurchases() {
        return ResponseEntity.ok(purchaseService.getAllPurchases());
    }
}
