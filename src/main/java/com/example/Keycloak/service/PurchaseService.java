package com.example.Keycloak.service;

import com.example.Keycloak.DTO.PurchaseDTO;

import java.util.List;

public interface PurchaseService {
    PurchaseDTO purchaseCar(Long carId, Long customerId);

    List<PurchaseDTO> getPurchasesByCustomerId(Long customerId);

    List<PurchaseDTO> getAllPurchases();
}
