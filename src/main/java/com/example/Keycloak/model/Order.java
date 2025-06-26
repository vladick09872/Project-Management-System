package com.example.Keycloak.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "orderDate")
    private LocalDate orderDate;

    @Column(name = "deliveryAdress")
    private String deliveryAddress;

    @Column(name = "orderStatus", length = 20)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne(mappedBy = "order")
    private OrderItem orderItem;

    @OneToMany(mappedBy = "order")
    private List<Delivery> deliveries;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
