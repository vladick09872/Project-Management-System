package com.example.Keycloak.DTO;

import java.time.LocalDate;

public record EnrollmentDTO(Long id,
                            LocalDate enrollmentDate,
                            boolean completed,
                            Long studentId,
                            Long courseId) {
}
