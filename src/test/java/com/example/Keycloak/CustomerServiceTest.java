package com.example.Keycloak;

import com.example.Keycloak.DTO.CustomerDTO;
import com.example.Keycloak.mapper.CustomerMapper;
import com.example.Keycloak.model.Customer;
import com.example.Keycloak.repository.CustomerRepository;
import com.example.Keycloak.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    private Customer customer;
    private CustomerDTO customerDTO;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        customer = new Customer();

        customer.setId(1L);
        customer.setName("Vladimir");
        customer.setEmail("vladimir@gmail.com");
        customer.setPhone("123456789");

        customerDTO = new CustomerDTO(1L, "Vladimir", "vladimir@gmail.com", "123456789", null, null);
    }

    @Test
    public void testRegisterCustomer() {
        Mockito.when(customerMapper.toModel(customerDTO)).thenReturn(customer);
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);
        Mockito.when(customerMapper.toDTO(customer)).thenReturn(customerDTO);

        CustomerDTO res = customerService.registerCustomer(customerDTO);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(customerDTO, res);

        Mockito.verify(customerMapper, Mockito.times(1)).toModel(customerDTO);
        Mockito.verify(customerRepository, Mockito.times(1)).save(customer);
        Mockito.verify(customerMapper, Mockito.times(1)).toDTO(customer);
    }

    @Test
    public void testGetCustomerById() {
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Mockito.when(customerMapper.toDTO(customer)).thenReturn(customerDTO);

        CustomerDTO res = customerService.getCustomerById(1L);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(customerDTO, res);

        Mockito.verify(customerRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(customerMapper, Mockito.times(1)).toDTO(customer);
    }
    
    @Test
    public void testGetAllCustomers() {
        Mockito.when(customerRepository.findAll()).thenReturn(List.of(customer));
        Mockito.when(customerMapper.toDTO(customer)).thenReturn(customerDTO);

        List<CustomerDTO> res = customerService.getAllCustomers();

        Assertions.assertNotNull(res);
        Assertions.assertEquals(List.of(customerDTO), res);

        Mockito.verify(customerRepository, Mockito.times(1)).findAll();
        Mockito.verify(customerMapper, Mockito.times(1)).toDTO(customer);
    }
}
