package com.example.Keycloak;

import com.example.Keycloak.DTO.TestDriveRequestDTO;
import com.example.Keycloak.mapper.TestDriveRequestMapper;
import com.example.Keycloak.model.Car;
import com.example.Keycloak.model.Customer;
import com.example.Keycloak.model.Status;
import com.example.Keycloak.model.TestDriveRequest;
import com.example.Keycloak.repository.CarRepository;
import com.example.Keycloak.repository.CustomerRepository;
import com.example.Keycloak.repository.TestDriveRequestRepository;
import com.example.Keycloak.service.impl.TestDriveRequestServiceImpl;
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
public class TestDriveRequestTest {
    private TestDriveRequest testDriveRequest;
    private TestDriveRequestDTO testDriveRequestDTO;
    private Car car;
    private Customer customer;

    @Mock
    private TestDriveRequestRepository testDriveRequestRepository;

    @Mock
    private TestDriveRequestMapper testDriveRequestMapper;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private TestDriveRequestServiceImpl testDriveRequestService;

    @BeforeEach
    void setUp() {
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

        testDriveRequest = new TestDriveRequest();

        testDriveRequest.setCar(car);
        testDriveRequest.setCustomer(customer);
        testDriveRequest.setId(1L);
        testDriveRequest.setRequestDate(LocalDate.now());
        testDriveRequest.setPreferredDate(LocalDate.now().plusDays(14));
        testDriveRequest.setStatus(Status.APPROVED);

        testDriveRequestDTO = new TestDriveRequestDTO(1L, LocalDate.now(), LocalDate.now().plusDays(14), Status.DECLINED, 1L, 1L);
    }

    @Test
    public void testRequestTestDrive() {
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Mockito.when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        Mockito.when(testDriveRequestRepository.save(Mockito.any(TestDriveRequest.class))).thenReturn(testDriveRequest);
        Mockito.when(testDriveRequestMapper.toDTO(testDriveRequest)).thenReturn(testDriveRequestDTO);

        TestDriveRequestDTO res = testDriveRequestService.requestTestDrive(1L, 1L, LocalDate.now().plusDays(14));

        Assertions.assertNotNull(res);
        Assertions.assertEquals(testDriveRequestDTO, res);

        Mockito.verify(customerRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(carRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(testDriveRequestMapper, Mockito.times(1)).toDTO(testDriveRequest);
    }

    @Test
    public void testApproveRequest() {
        Mockito.when(testDriveRequestRepository.findById(1L)).thenReturn(Optional.of(testDriveRequest));
        Mockito.when(testDriveRequestMapper.toDTO(testDriveRequest)).thenReturn(testDriveRequestDTO);

        TestDriveRequestDTO res = testDriveRequestService.approveRequest(1L);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(testDriveRequestDTO, res);

        Mockito.verify(testDriveRequestRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(testDriveRequestMapper, Mockito.times(1)).toDTO(testDriveRequest);
    }


    @Test
    public void testDeclineRequest() {
        Mockito.when(testDriveRequestRepository.findById(1L)).thenReturn(Optional.of(testDriveRequest));
        Mockito.when(testDriveRequestMapper.toDTO(testDriveRequest)).thenReturn(testDriveRequestDTO);

        TestDriveRequestDTO res = testDriveRequestService.declineRequest(1L);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(testDriveRequestDTO, res);

        Mockito.verify(testDriveRequestRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(testDriveRequestMapper, Mockito.times(1)).toDTO(testDriveRequest);
    }

    @Test
    public void testGetAllTestDriveRequests() {
        Mockito.when(testDriveRequestRepository.findAll()).thenReturn(List.of(testDriveRequest));
        Mockito.when(testDriveRequestMapper.toDTO(testDriveRequest)).thenReturn(testDriveRequestDTO);

        List<TestDriveRequestDTO> res = testDriveRequestService.getAllTestDriveRequests();

        Assertions.assertNotNull(res);
        Assertions.assertEquals(List.of(testDriveRequestDTO), res);

        Mockito.verify(testDriveRequestRepository, Mockito.times(1)).findAll();
        Mockito.verify(testDriveRequestMapper, Mockito.times(1)).toDTO(testDriveRequest);
    }

    @Test
    public void testGetTestDriveRequestById() {
        Mockito.when(testDriveRequestRepository.findById(1L)).thenReturn(Optional.of(testDriveRequest));
        Mockito.when(testDriveRequestMapper.toDTO(testDriveRequest)).thenReturn(testDriveRequestDTO);

        TestDriveRequestDTO res = testDriveRequestService.getTestDriveRequestById(1L);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(testDriveRequestDTO, res);

        Mockito.verify(testDriveRequestRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(testDriveRequestMapper, Mockito.times(1)).toDTO(testDriveRequest);
    }
}
