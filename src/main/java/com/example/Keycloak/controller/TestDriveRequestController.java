
package com.example.Keycloak.controller;

import com.example.Keycloak.DTO.CarDTO;
import com.example.Keycloak.DTO.TestDriveRequestDTO;
import com.example.Keycloak.service.TestDriveRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/testDriveRequest")
public class TestDriveRequestController {
    private final TestDriveRequestService testDriveRequestService;

    @Operation(summary = "Add new test drive request", description = "Add new test drive request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = CarDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid data request",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping("/request/{customerId}/{carId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<TestDriveRequestDTO> requestTestDrive(@PathVariable Long customerId, @PathVariable Long carId, @RequestParam LocalDate localDate) {
        return ResponseEntity.ok(testDriveRequestService.requestTestDrive(customerId, carId, localDate));
    }

    @Operation(summary = "Approve request", description = "Changes the request status to approved")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = CarDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid data request",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping("/approve/{requestId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TestDriveRequestDTO> approveRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(testDriveRequestService.approveRequest(requestId));
    }

    @Operation(summary = "Decline request", description = "Changes the request status to decline")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = CarDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid data request",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping("/decline/{requestId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TestDriveRequestDTO> declineRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(testDriveRequestService.declineRequest(requestId));
    }

    @Operation(summary = "Get all test drive request", description = "Return all test drive requests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = CarDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid data request",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TestDriveRequestDTO>> getAllTestDriveRequests() {
        return ResponseEntity.ok(testDriveRequestService.getAllTestDriveRequests());
    }

    @Operation(summary = "Get test drive by request ID", description = "Return test drive requests by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = CarDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid data request",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{requestId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TestDriveRequestDTO> getTestDriveRequestById(@PathVariable Long requestId) {
        return ResponseEntity.ok(testDriveRequestService.getTestDriveRequestById(requestId));
    }
}
