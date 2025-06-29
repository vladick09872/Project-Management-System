package com.example.Keycloak.service.impl;

import com.example.Keycloak.DTO.CustomerDTO;
import com.example.Keycloak.mapper.CustomerMapper;
import com.example.Keycloak.model.Customer;
import com.example.Keycloak.repository.CustomerRepository;
import com.example.Keycloak.service.CustomerService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional
    public CustomerDTO registerCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toModel(customerDTO);

        Customer saved = customerRepository.save(customer);

        return customerMapper.toDTO(saved);
    }

    @Override
    public CustomerDTO getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + customerId));

        return customerMapper.toDTO(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toDTO)
                .toList();
    }
}
