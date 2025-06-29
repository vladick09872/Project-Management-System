package com.example.Keycloak.repository;

import com.example.Keycloak.model.TestDriveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDriveRequestRepository extends JpaRepository<TestDriveRequest, Long> {
}
