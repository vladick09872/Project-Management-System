package com.example.Keycloak.service;

import com.example.Keycloak.DTO.EnrollmentDTO;

import java.util.List;

public interface EnrollmentService {
    EnrollmentDTO enrollStudentToCourse(Long studentId, Long courseId);

    List<EnrollmentDTO> getEnrollmentByStudentId(Long studentId);

    List<EnrollmentDTO> getAllEnrollments();

    void cancelEnrollment(Long enrollmentId);

    EnrollmentDTO markAsCompleted(Long enrollmentId);
}
