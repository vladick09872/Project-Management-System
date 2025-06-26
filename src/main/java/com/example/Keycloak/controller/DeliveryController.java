package com.example.Keycloak.controller;

import com.example.Keycloak.DTO.DeliveryDTO;
import com.example.Keycloak.DTO.UserDTO;
import com.example.Keycloak.model.DeliveryStatus;
import com.example.Keycloak.service.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.POST;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @Operation(summary = "Assign order to courier", description = "Assign current order to courier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })

    @PostMapping("/assign/{orderId}")
    @PreAuthorize("hasRole('MANAGER')")
    public DeliveryDTO assignCourier(@PathVariable Long orderId, @RequestParam String username) {
        return deliveryService.assignCourier(orderId, username);
    }

    @Operation(summary = "Get all deliveries", description = "Get all deliveries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/all")
    @PreAuthorize("hasRole('COURIER')")
    public List<DeliveryDTO> getDeliveriesByCourier(@RequestParam String username) {
        return deliveryService.getDeliveriesByCourier(username);
    }

    @Operation(summary = "Update delivery", description = "Update status in delivery")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })

    @PutMapping("/updateStatus/{deliveryId}")
    @PreAuthorize("hasRole('COURIER')")
    public DeliveryDTO updateDeliveryStatus(@PathVariable Long deliveryId, @RequestBody DeliveryStatus status) {
        return deliveryService.updateDeliveryStatus(deliveryId, status);
    }

    @Operation(summary = "Get delivery", description = "Get delivery by order ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('COURIER', 'MANAGER')")
    public DeliveryDTO getDeliveryByOrderId(@PathVariable Long orderId) {
        return deliveryService.getDeliveryByOrderId(orderId);
    }


}
