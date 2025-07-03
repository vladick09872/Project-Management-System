package com.example.Keycloak.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "role", length = 20)
    @Enumerated(EnumType.STRING)
    private USER_ROLE role;

    @OneToMany(mappedBy = "assignee")
    private List<Task> tasks;

    @OneToMany(mappedBy = "owner")
    private List<Project> projects;
}
