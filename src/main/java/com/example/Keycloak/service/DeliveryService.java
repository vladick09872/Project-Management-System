package com.example.Keycloak.service;

import com.example.Keycloak.DTO.DeliveryDTO;
import com.example.Keycloak.model.DeliveryStatus;

import java.util.List;

public interface DeliveryService {
    DeliveryDTO assignCourier(Long orderId, String courierUsername);

    List<DeliveryDTO> getDeliveriesByCourier(String courierUsername);

    DeliveryDTO updateDeliveryStatus(Long deliverId, DeliveryStatus newStatus);

    DeliveryDTO getDeliveryByOrderId(Long orderId);
}
