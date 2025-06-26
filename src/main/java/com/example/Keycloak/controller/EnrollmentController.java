package com.example.Keycloak.controller;

import com.example.Keycloak.DTO.EnrollmentDTO;
import com.example.Keycloak.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping("/enroll/student/{studentId}/to/course/{courseId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<EnrollmentDTO> enrollStudentToCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        return ResponseEntity.ok(enrollmentService.enrollStudentToCourse(studentId, courseId));
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<EnrollmentDTO>> getByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentByStudentId(studentId));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<List<EnrollmentDTO>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @DeleteMapping("cancel/{enrollmentId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> cancelEnrollment(@PathVariable Long enrollmentId) {
        enrollmentService.cancelEnrollment(enrollmentId);
        return ResponseEntity.noContent()
                .build();
    }

    @PutMapping("/complete/{enrollmentId}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<EnrollmentDTO> markAsCompleted(@PathVariable Long enrollmentId) {
        return ResponseEntity.ok(enrollmentService.markAsCompleted(enrollmentId));
    }
}
