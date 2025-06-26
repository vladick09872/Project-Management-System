package com.example.Keycloak.service.impl;

import com.example.Keycloak.DTO.EnrollmentDTO;
import com.example.Keycloak.mapper.EnrollmentMapper;
import com.example.Keycloak.model.Course;
import com.example.Keycloak.model.Enrollment;
import com.example.Keycloak.model.Student;
import com.example.Keycloak.repository.CourseRepository;
import com.example.Keycloak.repository.EnrollmentsRepository;
import com.example.Keycloak.repository.StudentRepository;
import com.example.Keycloak.service.EnrollmentService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentMapper enrollmentMapper;
    private final EnrollmentsRepository enrollmentsRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public EnrollmentDTO enrollStudentToCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found with id: " + studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found with id: " + courseId));

        if (course == null || student == null) {
            throw new NotFoundException("Student or Course not found with id: " + student + " or " + course);
        }

        Enrollment enrollment = new Enrollment();

        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setCompleted(false);

        Enrollment saved = enrollmentsRepository.save(enrollment);

        return enrollmentMapper.toDTO(saved);
    }

    @Override
    public List<EnrollmentDTO> getEnrollmentByStudentId(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found with id: " + studentId));

        List<Enrollment> enrollments = enrollmentsRepository.findByStudent(student);

        return enrollments.stream()
                .map(enrollmentMapper::toDTO)
                .toList();
    }

    @Override
    public List<EnrollmentDTO> getAllEnrollments() {
        return enrollmentsRepository.findAll().stream()
                .map(enrollmentMapper::toDTO)
                .toList();
    }

    @Override
    public void cancelEnrollment(Long enrollmentId) {
        if (!enrollmentsRepository.existsById(enrollmentId)) {
            throw new NotFoundException("Enrollment not found with id: " + enrollmentId);
        }
        enrollmentsRepository.deleteById(enrollmentId);
    }

    @Override
    public EnrollmentDTO markAsCompleted(Long enrollmentId) {
        Enrollment enrollment = enrollmentsRepository.findById(enrollmentId)
                .orElseThrow(() -> new NotFoundException("Enrollment not found with id: " + enrollmentId));

        enrollment.setCompleted(true);

        Enrollment saved = enrollmentsRepository.save(enrollment);

        return enrollmentMapper.toDTO(saved);
    }
}
