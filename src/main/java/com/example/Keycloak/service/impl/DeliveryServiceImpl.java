package com.example.Keycloak.service.impl;

import com.example.Keycloak.DTO.DeliveryDTO;
import com.example.Keycloak.mapper.DeliveryMapper;
import com.example.Keycloak.model.Delivery;
import com.example.Keycloak.model.DeliveryStatus;
import com.example.Keycloak.model.Role;
import com.example.Keycloak.model.User;
import com.example.Keycloak.repository.DeliveryRepository;
import com.example.Keycloak.repository.UserRepository;
import com.example.Keycloak.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;
    private final UserRepository userRepository;

    @Override
    public DeliveryDTO assignCourier(Long orderId, String courierUsername) {
        Delivery delivery = deliveryRepository.findByOrderId(orderId)
                .orElseThrow();

        User courier = userRepository.findByUsername(courierUsername)
                .orElseThrow();

        if (courier.getRole() != Role.COURIER) {
            throw new AccessDeniedException("You don't have permission to assign courier to this order.");
        }

        courier.setUsername(courierUsername);

        delivery.setCourier(courier);

        return deliveryMapper.toDTO(deliveryRepository.save(delivery));
    }

    @Override
    public List<DeliveryDTO> getDeliveriesByCourier(String courierUsername) {
        User user = userRepository.findByUsername(courierUsername)
                .orElseThrow();

        if (user.getRole() != Role.COURIER) {
            throw new AccessDeniedException("You don't have permission to assign courier to this order.");
        }

        return user.getDeliveries().stream()
                .map(deliveryMapper::toDTO)
                .toList();
    }

    @Override
    public DeliveryDTO updateDeliveryStatus(Long deliverId, DeliveryStatus newStatus) {
        Delivery delivery = deliveryRepository.findById(deliverId)
                .orElseThrow();

        newStatus = DeliveryStatus.DELIVERED;

        delivery.setStatus(newStatus);

        return deliveryMapper.toDTO(deliveryRepository.save(delivery));
    }

    @Override
    public DeliveryDTO getDeliveryByOrderId(Long orderId) {
        Delivery delivery = deliveryRepository.findByOrderId(orderId)
                .orElseThrow();

        return deliveryMapper.toDTO(delivery);
    }
}
