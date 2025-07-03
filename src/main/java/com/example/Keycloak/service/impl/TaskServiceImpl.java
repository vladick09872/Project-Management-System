package com.example.Keycloak.service.impl;

import com.example.Keycloak.DTO.TaskDTO;
import com.example.Keycloak.mapper.TaskMapper;
import com.example.Keycloak.model.Project;
import com.example.Keycloak.model.Status;
import com.example.Keycloak.model.Task;
import com.example.Keycloak.repository.ProjectRepository;
import com.example.Keycloak.repository.TaskRepository;
import com.example.Keycloak.service.TaskService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskDTO createTaskInProject(Long projectId, TaskDTO taskDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found with id: " + projectId));

        Task created = taskMapper.toModel(taskDTO);

        created.setProject(project);

        Task save = taskRepository.save(created);

        return taskMapper.toDTO(save);
    }

    @Override
    public TaskDTO getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found with id: " +taskId));

        return taskMapper.toDTO(task);
    }

    @Override
    public TaskDTO updateTask(Long taskId, TaskDTO taskDTO) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found with id: " +taskId));

        task.setDescription(taskDTO.description());
        task.setTitle(taskDTO.title());
        task.setStatus(taskDTO.status());

        Task save = taskRepository.save(task);

        return taskMapper.toDTO(save);
    }

    @Override
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new NotFoundException("Task not found with id: " + taskId);
        }
        taskRepository.deleteById(taskId);
    }

    @Override
    public TaskDTO changeTaskStatus(Long taskId, Status newStatus) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found with id: " +taskId));

        task.setStatus(newStatus);

        Task save = taskRepository.save(task);

        return taskMapper.toDTO(save);
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDTO)
                .toList();
    }
}
