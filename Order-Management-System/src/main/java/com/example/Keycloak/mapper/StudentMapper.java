package com.example.Keycloak.mapper;

import com.example.Keycloak.DTO.StudentDTO;
import com.example.Keycloak.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDTO toDTO(Student student);

    Student toModel(StudentDTO studentDTO);
}
