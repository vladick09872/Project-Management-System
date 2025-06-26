package com.example.Keycloak.repository;

import com.example.Keycloak.model.Enrollment;
import com.example.Keycloak.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentsRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent(Student student);
}
