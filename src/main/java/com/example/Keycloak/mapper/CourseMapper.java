package com.example.Keycloak.mapper;

import com.example.Keycloak.DTO.CourseDTO;
import com.example.Keycloak.model.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDTO toDTO(Course course);
    Course toModel(CourseDTO courseDTO);
}
