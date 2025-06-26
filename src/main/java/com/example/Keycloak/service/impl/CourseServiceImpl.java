package com.example.Keycloak.service.impl;

import com.example.Keycloak.DTO.CourseDTO;
import com.example.Keycloak.mapper.CourseMapper;
import com.example.Keycloak.model.Course;
import com.example.Keycloak.repository.CourseRepository;
import com.example.Keycloak.service.CourseService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toDTO)
                .toList();
    }

    @Override
    public CourseDTO getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found with id: " + courseId));

        return courseMapper.toDTO(course);
    }

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = courseMapper.toModel(courseDTO);

        Course saved = courseRepository.save(course);

        return courseMapper.toDTO(saved);
    }

    @Override
    public CourseDTO updateCourse(Long courseId, CourseDTO courseDTO) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found with id: " + courseId));

        course.setId(courseDTO.id());
        course.setDescription(courseDTO.description());
        course.setCapacity(courseDTO.capacity());
        course.setTitle(courseDTO.title());

        Course saved = courseRepository.save(course);

        return courseMapper.toDTO(saved);
    }

    @Override
    public void deleteCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new NotFoundException("Course not found with id: " + courseId);
        }
        courseRepository.deleteById(courseId);
    }
}
