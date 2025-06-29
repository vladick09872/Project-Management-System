package com.example.Keycloak.repository;

import com.example.Keycloak.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("""
            select c from Car c inner join Customer cust on
            c.id = cust.id where c.inStock = true
            """)
    List<Car> findByInStock(boolean inStock);

}
