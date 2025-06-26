package com.example.Keycloak.service.impl;

import com.example.Keycloak.DTO.OrderDTO;
import com.example.Keycloak.mapper.OrderMapper;
import com.example.Keycloak.model.Order;
import com.example.Keycloak.model.OrderStatus;
import com.example.Keycloak.model.User;
import com.example.Keycloak.repository.OrderRepository;
import com.example.Keycloak.repository.UserRepository;
import com.example.Keycloak.service.OrderService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        return orderMapper.toDTO(orderRepository.save(orderMapper.toEntity(orderDTO)));
    }

    @Override
    public OrderDTO getOrdersByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found."));

        return orderMapper.toDTO(orderRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("Order not found.")));
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    public OrderDTO updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found."));

        order.setOrderStatus(newStatus);

        return orderMapper.toDTO(orderRepository.save(order));
    }

    @Override
    public void cancelOrder(Long orderId, String username) {
        if (!orderRepository.existsById(orderId)) {
            throw new NotFoundException("Order not found with id: " + orderId);
        }

        orderRepository.deleteById(orderId);
    }
}
