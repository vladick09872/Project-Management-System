package com.example.Keycloak.mapper;

import com.example.Keycloak.DTO.TestDriveRequestDTO;
import com.example.Keycloak.model.TestDriveRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestDriveRequestMapper {
    TestDriveRequestDTO toDTO(TestDriveRequest testDriveRequest);

    TestDriveRequest toModel(TestDriveRequestDTO testDriveRequestDTO);
}
