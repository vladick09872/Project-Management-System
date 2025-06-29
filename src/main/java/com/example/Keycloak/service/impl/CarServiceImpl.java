package com.example.Keycloak.service.impl;

import com.example.Keycloak.DTO.CarDTO;
import com.example.Keycloak.mapper.CarMapper;
import com.example.Keycloak.model.Car;
import com.example.Keycloak.repository.CarRepository;
import com.example.Keycloak.service.CarService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarMapper carMapper;
    private final CarRepository carRepository;

    @Override
    public CarDTO addCar(CarDTO carDTO) {
        Car car = carMapper.toModel(carDTO);

        Car saved = carRepository.save(car);

        return carMapper.toDTO(saved);
    }

    @Override
    public List<CarDTO> getAllCars() {
        return carRepository.findAll().stream()
                .map(carMapper::toDTO)
                .toList();
    }

    @Override
    public CarDTO getCarById(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Car not found with id: " + carId));

        return carMapper.toDTO(car);
    }

    @Override
    @Transactional
    public CarDTO updateCar(Long carId, CarDTO carDTO) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Car not found with id: " + carId));

        if (car == null) {
            throw new NotFoundException("Car not found with id: " + carId);
        }
        car.setId(carDTO.id());
        car.setBrand(carDTO.brand());
        car.setModel(carDTO.model());
        car.setYear(carDTO.year());
        car.setPrice(carDTO.price());
        car.setInStock(carDTO.inStock());

        Car saved = carRepository.save(car);

        return carMapper.toDTO(saved);
    }

    @Override
    public void deleteCar(Long carId) {
        if (!carRepository.existsById(carId)) {
            throw new NotFoundException("Car not found with id: " + carId);
        }
        carRepository.deleteById(carId);
    }

    @Override
    public List<CarDTO> getAvailableCars() {
        return carRepository.findByInStock(true).stream()
                .map(carMapper::toDTO)
                .toList();
    }
}
