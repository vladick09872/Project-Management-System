package com.example.Keycloak.service;

import com.example.Keycloak.DTO.TestDriveRequestDTO;

import java.time.LocalDate;
import java.util.List;

public interface TestDriveRequestService {
    TestDriveRequestDTO requestTestDrive(Long customerId, Long carId,
                                         LocalDate preferredDate);

    TestDriveRequestDTO approveRequest(Long testDriveRequestId);

    TestDriveRequestDTO declineRequest(Long testDriveRequestId);

    List<TestDriveRequestDTO> getAllTestDriveRequests();

    TestDriveRequestDTO getTestDriveRequestById(Long testDriveRequestId);
}
