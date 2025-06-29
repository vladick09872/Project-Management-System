package com.example.Keycloak.service;

import com.example.Keycloak.DTO.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO registerCustomer(CustomerDTO customerDTO);

    CustomerDTO getCustomerById(Long customerId);

    List<CustomerDTO> getAllCustomers();
}
