package com.example.Keycloak.mapper;

import com.example.Keycloak.DTO.CarDTO;
import com.example.Keycloak.model.Car;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {
    CarDTO toDTO(Car car);

    Car toModel(CarDTO carDTO);
}
