package com.example.Keycloak.tests;

import com.example.Keycloak.DTO.EnrollmentDTO;
import com.example.Keycloak.mapper.EnrollmentMapper;
import com.example.Keycloak.model.Course;
import com.example.Keycloak.model.Enrollment;
import com.example.Keycloak.model.Student;
import com.example.Keycloak.repository.CourseRepository;
import com.example.Keycloak.repository.EnrollmentsRepository;
import com.example.Keycloak.repository.StudentRepository;
import com.example.Keycloak.service.impl.EnrollmentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceTest {
    private Enrollment enrollment;
    private EnrollmentDTO enrollmentDTO;

    private Student student;
    private Course course;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private EnrollmentsRepository enrollmentsRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EnrollmentMapper enrollmentMapper;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @BeforeEach
    void setUp() {
        student = new Student();

        student.setId(1L);
        student.setName("John Doe");
        student.setEmail("Email123");

        course = new Course();

        course.setId(1L);
        course.setCapacity(10);
        course.setTitle("Title");
        course.setDescription("Description");

        enrollment = new Enrollment();

        enrollment.setCourse(course);
        enrollment.setStudent(student);

        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setId(1L);
        enrollment.setCompleted(false);

        enrollmentDTO = new EnrollmentDTO(1L, LocalDate.now(), false, 1L, 1L);
    }

    @Test
    public void testEnrollStudentToCourse() {
        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Mockito.when(enrollmentsRepository.save(Mockito.any(Enrollment.class))).thenReturn(enrollment);
        Mockito.when(enrollmentMapper.toDTO(enrollment)).thenReturn(enrollmentDTO);

        EnrollmentDTO res = enrollmentService.enrollStudentToCourse(1L, 1L);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(enrollmentDTO, res);

        Mockito.verify(studentRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(courseRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(enrollmentsRepository, Mockito.times(1)).save(Mockito.any(Enrollment.class));
        Mockito.verify(enrollmentMapper, Mockito.times(1)).toDTO(enrollment);
    }

    @Test
    public void testGetEnrollmentByStudentId() {
        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Mockito.when(enrollmentsRepository.findByStudent(student)).thenReturn(List.of(enrollment));
        Mockito.when(enrollmentMapper.toDTO(enrollment)).thenReturn(enrollmentDTO);

        List<EnrollmentDTO> res = enrollmentService.getEnrollmentByStudentId(1L);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(List.of(enrollmentDTO), res);

        Mockito.verify(studentRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(enrollmentsRepository, Mockito.times(1)).findByStudent(student);
        Mockito.verify(enrollmentMapper, Mockito.times(1)).toDTO(enrollment);
    }

    @Test
    public void testGetAllEnrollments() {
        Mockito.when(enrollmentsRepository.findAll()).thenReturn(List.of(enrollment));
        Mockito.when(enrollmentMapper.toDTO(enrollment)).thenReturn(enrollmentDTO);

        List<EnrollmentDTO> res = enrollmentService.getAllEnrollments();

        Assertions.assertNotNull(res);
        Assertions.assertEquals(List.of(enrollmentDTO), res);

        Mockito.verify(enrollmentsRepository, Mockito.times(1)).findAll();
        Mockito.verify(enrollmentMapper, Mockito.times(1)).toDTO(enrollment);
    }

    @Test
    public void testCancelEnrollment() {
        Mockito.when(enrollmentsRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(enrollmentsRepository).deleteById(1L);

        enrollmentService.cancelEnrollment(1L);

        Assertions.assertNotNull(enrollment);
        Assertions.assertTrue(true);

        Mockito.verify(enrollmentsRepository, Mockito.times(1)).existsById(1L);
        Mockito.verify(enrollmentsRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void testMarkAsCompleted() {
        Mockito.when(enrollmentsRepository.findById(1L)).thenReturn(Optional.of(enrollment));
        Mockito.when(enrollmentsRepository.save(Mockito.any(Enrollment.class))).thenReturn(enrollment);
        Mockito.when(enrollmentMapper.toDTO(enrollment)).thenReturn(enrollmentDTO);

        EnrollmentDTO res = enrollmentService.markAsCompleted(1L);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(enrollmentDTO, res);

        Mockito.verify(enrollmentsRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(enrollmentsRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(enrollmentMapper, Mockito.times(1)).toDTO(enrollment);
    }
}
