package com.example.Keycloak.service.impl;

import com.example.Keycloak.DTO.OrderItemDTO;
import com.example.Keycloak.mapper.OrderItemMapper;
import com.example.Keycloak.model.OrderItem;
import com.example.Keycloak.repository.OrderItemRepository;
import com.example.Keycloak.service.OrderItemService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemDTO> getItemsByOrderId(Long orderId) {
        Optional<OrderItem> orderItemDTOS = orderItemRepository.findByOrderId(orderId);

        return orderItemDTOS.stream()
                .map(orderItemMapper::toDTO)
                .toList();
    }
}
