package com.example.Keycloak.mapper;

import com.example.Keycloak.DTO.TaskDTO;
import com.example.Keycloak.model.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDTO toDTO(Task task);

    Task toModel(TaskDTO taskDTO);
}
