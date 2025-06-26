package com.example.Keycloak.tests;

import com.example.Keycloak.DTO.CourseDTO;
import com.example.Keycloak.mapper.CourseMapper;
import com.example.Keycloak.model.Course;
import com.example.Keycloak.repository.CourseRepository;
import com.example.Keycloak.service.impl.CourseServiceImpl;
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
public class CourseServiceTest {
    private Course course;
    private CourseDTO courseDTO;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    void setUp() {
        course = new Course();

        course.setId(1L);
        course.setCapacity(10);
        course.setTitle("Title");
        course.setDescription("Description");

        courseDTO = new CourseDTO(1L, "Title", "Description", 10, null);
    }

    @Test
    public void testGetAllCourses() {
        Mockito.when(courseRepository.findAll()).thenReturn(List.of(course));
        Mockito.when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        List<CourseDTO> res = courseService.getAllCourses();

        Assertions.assertNotNull(res);
        Assertions.assertEquals(List.of(courseDTO), res);

        Mockito.verify(courseRepository, Mockito.times(1)).findAll();
        Mockito.verify(courseMapper, Mockito.times(1)).toDTO(course);
    }

    @Test
    public void testGetCourseById() {
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Mockito.when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        CourseDTO res = courseService.getCourseById(1L);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(courseDTO, res);

        Mockito.verify(courseRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(courseMapper, Mockito.times(1)).toDTO(course);
    }

    @Test
    public void testCreateCourse() {
        Mockito.when(courseMapper.toModel(courseDTO)).thenReturn(course);
        Mockito.when(courseRepository.save(course)).thenReturn(course);
        Mockito.when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        CourseDTO res = courseService.createCourse(courseDTO);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(courseDTO, res);

        Mockito.verify(courseMapper, Mockito.times(1)).toModel(courseDTO);
        Mockito.verify(courseRepository, Mockito.times(1)).save(course);
        Mockito.verify(courseMapper, Mockito.times(1)).toDTO(course);
    }

    @Test
    public void testUpdateCourse() {
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Mockito.when(courseRepository.save(course)).thenReturn(course);
        Mockito.when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        CourseDTO res = courseService.updateCourse(1L, courseDTO);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(courseDTO, res);

        Mockito.verify(courseRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(courseRepository, Mockito.times(1)).save(course);
        Mockito.verify(courseMapper, Mockito.times(1)).toDTO(course);
    }
}
