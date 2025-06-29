package com.example.Keycloak;

import com.example.Keycloak.DTO.PurchaseDTO;
import com.example.Keycloak.mapper.PurchaseMapper;
import com.example.Keycloak.model.Car;
import com.example.Keycloak.model.Customer;
import com.example.Keycloak.model.Purchase;
import com.example.Keycloak.repository.CarRepository;
import com.example.Keycloak.repository.CustomerRepository;
import com.example.Keycloak.repository.PurchaseRepository;
import com.example.Keycloak.service.impl.PurchaseServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceImplTest {
    private Purchase purchase;
    private PurchaseDTO purchaseDTO;

    private Car car;
    private Customer customer;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private PurchaseMapper purchaseMapper;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    @BeforeEach
    void setup() {
        car = new Car();

        car.setId(1L);
        car.setInStock(true);
        car.setYear(2019);
        car.setPrice(100000);
        car.setModel("M3");
        car.setBrand("BMW");

        customer = new Customer();

        customer.setId(1L);
        customer.setName("Vladimir");
        customer.setEmail("vladimir@gmail.com");
        customer.setPhone("123456789");

        purchase = new Purchase();

        purchase.setCustomer(customer);
        purchase.setCar(car);
        purchase.setId(1L);
        purchase.setPriceAtPurchase(10000.0);
        purchase.setPurchaseDate(LocalDate.now());

        purchaseDTO = new PurchaseDTO(1L, LocalDate.now(), 10000.0, 1L, 1L);
    }

    @Test
    public void testPurchaseCar() {
        Mockito.when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Mockito.when(purchaseRepository.save(Mockito.any(Purchase.class))).thenReturn(purchase);
        Mockito.when(purchaseMapper.toDTO(purchase)).thenReturn(purchaseDTO);

        PurchaseDTO res = purchaseService.purchaseCar(1L, 1L);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(purchaseDTO, res);

        Mockito.verify(carRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(customerRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(purchaseRepository, Mockito.times(1)).save(Mockito.any(Purchase.class));
        Mockito.verify(purchaseMapper, Mockito.times(1)).toDTO(purchase);
    }

    @Test
    public void testGetPurchaseByCustomerId() {
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Mockito.when(purchaseRepository.findByCustomer(customer)).thenReturn(List.of(purchase));
        Mockito.when(purchaseMapper.toDTO(purchase)).thenReturn(purchaseDTO);

        List<PurchaseDTO> res = purchaseService.getPurchasesByCustomerId(1L);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(List.of(purchaseDTO), res);

        Mockito.verify(customerRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(purchaseRepository, Mockito.times(1)).findByCustomer(customer);
        Mockito.verify(purchaseMapper, Mockito.times(1)).toDTO(purchase);
    }

    @Test
    public void testGetAllPurchases() {
        Mockito.when(purchaseRepository.findAll()).thenReturn(List.of(purchase));
        Mockito.when(purchaseMapper.toDTO(purchase)).thenReturn(purchaseDTO);

        List<PurchaseDTO> res = purchaseService.getAllPurchases();

        Assertions.assertNotNull(res);
        Assertions.assertEquals(List.of(purchaseDTO), res);

        Mockito.verify(purchaseRepository, Mockito.times(1)).findAll();
        Mockito.verify(purchaseMapper, Mockito.times(1)).toDTO(purchase);
    }
}
