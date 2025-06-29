package com.example.Keycloak.service.impl;

import com.example.Keycloak.DTO.PurchaseDTO;
import com.example.Keycloak.mapper.PurchaseMapper;
import com.example.Keycloak.model.Car;
import com.example.Keycloak.model.Customer;
import com.example.Keycloak.model.Purchase;
import com.example.Keycloak.repository.CarRepository;
import com.example.Keycloak.repository.CustomerRepository;
import com.example.Keycloak.repository.PurchaseRepository;
import com.example.Keycloak.service.PurchaseService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseMapper purchaseMapper;
    private final PurchaseRepository purchaseRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public PurchaseDTO purchaseCar(Long carId, Long customerId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Car not found with id: " + carId));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + customerId));

        Purchase purchase = new Purchase();

        purchase.setCar(car);
        purchase.setCustomer(customer);

        Purchase saved = purchaseRepository.save(purchase);

        return purchaseMapper.toDTO(saved);
    }

    @Override
    public List<PurchaseDTO> getPurchasesByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + customerId));

        List<Purchase> purchases = purchaseRepository.findByCustomer(customer);

        return purchases.stream()
                .map(purchaseMapper::toDTO)
                .toList();
    }

    @Override
    public List<PurchaseDTO> getAllPurchases() {
        return purchaseRepository.findAll().stream()
                .map(purchaseMapper::toDTO)
                .toList();
    }
}
