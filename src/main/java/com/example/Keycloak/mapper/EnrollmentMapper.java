package com.example.Keycloak.mapper;

import com.example.Keycloak.DTO.EnrollmentDTO;
import com.example.Keycloak.model.Enrollment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {
    EnrollmentDTO toDTO(Enrollment enrollment);

    Enrollment toModel(EnrollmentDTO enrollmentDTO);

}
