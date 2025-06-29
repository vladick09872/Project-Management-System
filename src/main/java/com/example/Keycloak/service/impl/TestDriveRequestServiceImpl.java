package com.example.Keycloak.service.impl;

import com.example.Keycloak.DTO.TestDriveRequestDTO;
import com.example.Keycloak.mapper.TestDriveRequestMapper;
import com.example.Keycloak.model.Car;
import com.example.Keycloak.model.Customer;
import com.example.Keycloak.model.Status;
import com.example.Keycloak.model.TestDriveRequest;
import com.example.Keycloak.repository.CarRepository;
import com.example.Keycloak.repository.CustomerRepository;
import com.example.Keycloak.repository.TestDriveRequestRepository;
import com.example.Keycloak.service.TestDriveRequestService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestDriveRequestServiceImpl implements TestDriveRequestService {
    private final TestDriveRequestMapper testDriveRequestMapper;
    private final TestDriveRequestRepository testDriveRequestRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;

    @Override
    @Transactional
    public TestDriveRequestDTO requestTestDrive(Long customerId, Long carId, LocalDate preferredDate) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + customerId));

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Car not found with id: " + carId));

        TestDriveRequest testDriveRequest = new TestDriveRequest();

        testDriveRequest.setCustomer(customer);
        testDriveRequest.setCar(car);
        testDriveRequest.setPreferredDate(preferredDate);

        TestDriveRequest saved = testDriveRequestRepository.save(testDriveRequest);

        return testDriveRequestMapper.toDTO(saved);
    }

    @Override
    public TestDriveRequestDTO approveRequest(Long testDriveRequestId) {
        TestDriveRequest testDriveRequest = testDriveRequestRepository.findById(testDriveRequestId)
                .orElseThrow(() -> new NotFoundException("Test drive request not found with id: " + testDriveRequestId));

        if (testDriveRequest == null) {
            throw new NotFoundException("Test drive request not found with id: " + testDriveRequestId);
        }

        testDriveRequest.setStatus(Status.APPROVED);

        return testDriveRequestMapper.toDTO(testDriveRequest);
    }

    @Override
    public TestDriveRequestDTO declineRequest(Long testDriveRequestId) {
        TestDriveRequest testDriveRequest = testDriveRequestRepository.findById(testDriveRequestId)
                .orElseThrow(() -> new NotFoundException("Test drive request not found with id: " + testDriveRequestId));

        if (testDriveRequest == null) {
            throw new NotFoundException("Test drive request not found with id: " + testDriveRequestId);
        }

        testDriveRequest.setStatus(Status.DECLINED);

        return testDriveRequestMapper.toDTO(testDriveRequest);
    }

    @Override
    public List<TestDriveRequestDTO> getAllTestDriveRequests() {
        return testDriveRequestRepository.findAll().stream()
                .map(testDriveRequestMapper::toDTO)
                .toList();
    }

    @Override
    public TestDriveRequestDTO getTestDriveRequestById(Long testDriveRequestId) {
        TestDriveRequest testDriveRequest = testDriveRequestRepository.findById(testDriveRequestId)
                .orElseThrow(() -> new NotFoundException("Test drive request not found with id: " + testDriveRequestId));

        return testDriveRequestMapper.toDTO(testDriveRequest);
    }
}
