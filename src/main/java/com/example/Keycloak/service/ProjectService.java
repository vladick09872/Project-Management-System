package com.example.Keycloak.service;

import com.example.Keycloak.DTO.ProjectDTO;

import java.util.List;

public interface ProjectService {
    ProjectDTO createProject(ProjectDTO projectDTO);

    ProjectDTO getProjectById(Long projectId);

    ProjectDTO updateProject(Long projectId, ProjectDTO projectDTO);

    void deleteProject(Long projectId);

    ProjectDTO removeUserToProject(Long projectId, Long userId);

    ProjectDTO addUserToProject(Long projectId, Long userId);

    List<ProjectDTO> getAllProjects();
}
