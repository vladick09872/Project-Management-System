package com.example.Keycloak.controller;

import com.example.Keycloak.DTO.TaskDTO;
import com.example.Keycloak.DTO.UserDTO;
import com.example.Keycloak.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
@Tag(name = "Task management", description = "API'S for managing tasks")
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = "Create new task", description = "Create new task in project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = TaskDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping("/create/{projectId}/task")
    @PreAuthorize("hasAnyRole('DEVELOPER', 'MANAGER')")
    public ResponseEntity<TaskDTO> createTaskInProject(@PathVariable Long projectId, @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.createTaskInProject(projectId, taskDTO));
    }

    @Operation(summary = "Get task by ID", description = "Get current task by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = TaskDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Operation(summary = "Update task", description = "Update current task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = TaskDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.updateTask(id, taskDTO));
    }

    @Operation(summary = "Delete task", description = "Delete task by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = TaskDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Change task status", description = "Change new status for task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = TaskDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping("/status/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<TaskDTO> changeTaskStatus(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.changeTaskStatus(id, taskDTO.status()));
    }

    @Operation(summary = "Get all tasks", description = "Get all tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = TaskDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }
}
