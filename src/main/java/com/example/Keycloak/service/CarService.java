package com.example.Keycloak.service;

import com.example.Keycloak.DTO.CarDTO;

import java.util.List;

public interface CarService {
    CarDTO addCar(CarDTO carDTO);

    List<CarDTO> getAllCars();

    CarDTO getCarById(Long carId);

    CarDTO updateCar(Long carId, CarDTO carDTO);

    void deleteCar(Long carId);

    List<CarDTO> getAvailableCars();
}
