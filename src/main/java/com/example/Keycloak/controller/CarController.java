package com.example.Keycloak.controller;

import com.example.Keycloak.DTO.CarDTO;
import com.example.Keycloak.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
@Tag(name = "Car management", description = "API's for managing cars")
public class CarController {
    private final CarService carService;

    @Operation(summary = "Add new Car", description = "Adds new car to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = CarDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid data request",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarDTO> addCar(@RequestBody CarDTO carDTO) {
        return ResponseEntity.ok(carService.addCar(carDTO));
    }

    @Operation(summary = "Get all cars", description = "Get all in system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = CarDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid data request",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/all")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<CarDTO>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @Operation(summary = "Get car by ID", description = "Get current car by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = CarDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid data request",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{carId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Long carId) {
        return ResponseEntity.ok(carService.getCarById(carId));
    }

    @Operation(summary = "Update car", description = "Updates current car to new car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = CarDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid data request",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping("/update/{carId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarDTO> updateCar(@PathVariable Long carId, @RequestBody CarDTO carDTO) {
        return ResponseEntity.ok(carService.updateCar(carId, carDTO));
    }

    @Operation(summary = "Delete car", description = "Delete car in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = CarDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid data request",
                    content = @Content(schema = @Schema()))
    })
    @DeleteMapping("/delete/{carId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Get available car", description = "Return only available cars")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = CarDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid data request",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/available")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<CarDTO>> getAvailableCars() {
        return ResponseEntity.ok(carService.getAvailableCars());
    }
}
