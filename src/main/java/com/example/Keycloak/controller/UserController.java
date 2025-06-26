package com.example.Keycloak.controller;

import com.example.Keycloak.DTO.UserDTO;
import com.example.Keycloak.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User management", description = "APIs for managing users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get current user", description = "Get current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/current")
    @PreAuthorize("hasRole('MANAGER')")
    public UserDTO getCurrentUser() {
        return userService.getCurrentUser();
    }

    @Operation(summary = "Get all users with role - courier", description = "Get current user with courier role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })

    @GetMapping("/all/couriers")
    @PreAuthorize("hasRole('MANAGER')")
    public List<UserDTO> getAllCouriers() {
        return userService.getAllCouriers();
    }

}
