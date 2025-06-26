package com.example.Keycloak.service;

import com.example.Keycloak.DTO.CourseDTO;

import java.util.List;

public interface CourseService {
    List<CourseDTO> getAllCourses();

    CourseDTO getCourseById(Long courseId);

    CourseDTO createCourse(CourseDTO courseDTO);

    CourseDTO updateCourse(Long courseId, CourseDTO courseDTO);

    void deleteCourse(Long courseId);
}
