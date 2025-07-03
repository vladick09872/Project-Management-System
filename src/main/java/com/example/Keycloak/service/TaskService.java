package com.example.Keycloak.service;

import com.example.Keycloak.DTO.TaskDTO;
import com.example.Keycloak.model.Status;

import java.util.List;

public interface TaskService {
    TaskDTO createTaskInProject(Long projectId, TaskDTO taskDTO);

    TaskDTO getTaskById(Long taskId);

    TaskDTO updateTask(Long taskId, TaskDTO taskDTO);

    void deleteTask(Long taskId);

    TaskDTO changeTaskStatus(Long taskId, Status newStatus);

    List<TaskDTO> getAllTasks();
}
