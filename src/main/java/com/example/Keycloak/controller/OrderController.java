package com.example.Keycloak.controller;

import com.example.Keycloak.DTO.OrderDTO;
import com.example.Keycloak.DTO.UserDTO;
import com.example.Keycloak.model.OrderStatus;
import com.example.Keycloak.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Create new order", description = "Add new order to system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @Operation(summary = "Get order by username", description = "Get order by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{username}")
    @PreAuthorize("hasRole('COURIER')")
    public OrderDTO getOrdersByUser(@PathVariable String username) {
        return orderService.getOrdersByUser(username);
    }

    @Operation(summary = "Get all orders", description = "Get all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/all")
    @PreAuthorize("hasRole('COURIER')")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @Operation(summary = "Update order", description = "Update order by order ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping("/update/{orderId}")
    @PreAuthorize("hasRole('MANAGER')")
    public OrderDTO update(@PathVariable Long orderId, @RequestBody OrderStatus status) {
        return orderService.updateOrderStatus(orderId, status);
    }

    @Operation(summary = "Delete order", description = "Delete current order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })

    @DeleteMapping("/{orderId}/cancel/{username}")
    @PreAuthorize("hasRole('USER')")
    public void cancelOrder(@PathVariable Long orderId, @PathVariable String username) {
        orderService.cancelOrder(orderId, username);
    }
}
