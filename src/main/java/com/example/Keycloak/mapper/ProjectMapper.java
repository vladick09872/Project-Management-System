package com.example.Keycloak.mapper;

import com.example.Keycloak.DTO.ProjectDTO;
import com.example.Keycloak.model.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectDTO toDTO(Project project);

    Project toModel(ProjectDTO projectDTO);
}
