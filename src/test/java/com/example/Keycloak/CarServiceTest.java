package com.example.Keycloak;

import com.example.Keycloak.DTO.CarDTO;
import com.example.Keycloak.mapper.CarMapper;
import com.example.Keycloak.model.Car;
import com.example.Keycloak.repository.CarRepository;
import com.example.Keycloak.service.impl.CarServiceImpl;
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
public class CarServiceTest {
    private Car car;
    private CarDTO carDTO;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarMapper carMapper;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        car = new Car();

        car.setId(1L);
        car.setInStock(true);
        car.setYear(2019);
        car.setPrice(100000);
        car.setModel("M3");
        car.setBrand("BMW");

        carDTO = new CarDTO(1L, "BMW", "M3", 2019, 100000, true, null, null);
    }

    @Test
    public void testAddCar() {
        Mockito.when(carMapper.toModel(carDTO)).thenReturn(car);
        Mockito.when(carRepository.save(car)).thenReturn(car);
        Mockito.when(carMapper.toDTO(car)).thenReturn(carDTO);

        CarDTO res = carService.addCar(carDTO);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(carDTO, res);

        Mockito.verify(carMapper, Mockito.times(1)).toModel(carDTO);
        Mockito.verify(carRepository, Mockito.times(1)).save(car);
        Mockito.verify(carMapper, Mockito.times(1)).toDTO(car);
    }

    @Test
    public void testGetAllCars() {
        Mockito.when(carRepository.findAll()).thenReturn(List.of(car));
        Mockito.when(carMapper.toDTO(car)).thenReturn(carDTO);

        List<CarDTO> res = carService.getAllCars();

        Assertions.assertNotNull(res);
        Assertions.assertEquals(List.of(carDTO), res);

        Mockito.verify(carRepository, Mockito.times(1)).findAll();
        Mockito.verify(carMapper, Mockito.times(1)).toDTO(car);
    }

    @Test
    public void testGetCarById() {
        Mockito.when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        Mockito.when(carMapper.toDTO(car)).thenReturn(carDTO);

        CarDTO res = carService.getCarById(1L);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(carDTO.id(), res.id());

        Mockito.verify(carMapper, Mockito.times(1)).toDTO(car);
        Mockito.verify(carRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testUpdateCar() {
        Mockito.when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        Mockito.when(carRepository.save(car)).thenReturn(car);
        Mockito.when(carMapper.toDTO(car)).thenReturn(carDTO);

        CarDTO res = carService.updateCar(1L, carDTO);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(carDTO, res);

        Mockito.verify(carRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(carRepository, Mockito.times(1)).save(car);
        Mockito.verify(carMapper, Mockito.times(1)).toDTO(car);
    }

    @Test
    public void testDeleteCar() {
        Mockito.when(carRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(carRepository).deleteById(1L);

        carService.deleteCar(1L);

        Assertions.assertNotNull(car);
        Assertions.assertTrue(true);

        Mockito.verify(carRepository, Mockito.times(1)).existsById(1L);
        Mockito.verify(carRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void testGetAvailableCars() {
        Mockito.when(carRepository.findByInStock(true)).thenReturn(List.of(car));
        Mockito.when(carMapper.toDTO(car)).thenReturn(carDTO);

        List<CarDTO> res = carService.getAvailableCars();

        Assertions.assertNotNull(res);
        Assertions.assertEquals(List.of(carDTO), res);

        Mockito.verify(carRepository, Mockito.times(1)).findByInStock(true);
        Mockito.verify(carMapper, Mockito.times(1)).toDTO(car);

    }
}
