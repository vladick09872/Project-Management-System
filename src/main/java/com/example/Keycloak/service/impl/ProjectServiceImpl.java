package com.example.Keycloak.service.impl;

import com.example.Keycloak.DTO.ProjectDTO;
import com.example.Keycloak.mapper.ProjectMapper;
import com.example.Keycloak.model.Project;
import com.example.Keycloak.model.User;
import com.example.Keycloak.repository.ProjectRepository;
import com.example.Keycloak.repository.UserRepository;
import com.example.Keycloak.service.ProjectService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = projectMapper.toModel(projectDTO);

        Project save = projectRepository.save(project);

        return projectMapper.toDTO(save);
    }

    @Override
    public ProjectDTO getProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found with id: " + projectId));

        return projectMapper.toDTO(project);
    }

    @Override
    public ProjectDTO updateProject(Long projectId, ProjectDTO projectDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found with id: " + projectId));

        project.setName(projectDTO.name());
        project.setDescription(projectDTO.description());

        Project save = projectRepository.save(project);

        return projectMapper.toDTO(save);
    }

    @Override
    public void deleteProject(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new NotFoundException("Project not found with id: " + projectId);
        }
        projectRepository.deleteById(projectId);
    }

    @Override
    public ProjectDTO removeUserToProject(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found with id: " + projectId));

        userRepository.deleteById(userId);

        Project save = projectRepository.save(project);

        return projectMapper.toDTO(save);
    }

    @Override
    public ProjectDTO addUserToProject(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found with id: " + projectId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user not found with id: " + userId));

        project.setOwner(user);

        Project save = projectRepository.save(project);

        return projectMapper.toDTO(save);
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toDTO)
                .toList();
    }
}
