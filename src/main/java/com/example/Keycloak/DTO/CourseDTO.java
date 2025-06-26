package com.example.Keycloak.DTO;

public record CourseDTO(Long id,
                        String title,
                        String description,
                        int capacity,
                        Long enrollmentId) {
}
