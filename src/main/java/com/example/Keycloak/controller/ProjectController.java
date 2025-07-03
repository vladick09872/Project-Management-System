package com.example.Keycloak.controller;

import com.example.Keycloak.DTO.ProjectDTO;
import com.example.Keycloak.service.ProjectService;
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
@RequestMapping("/projects")
@Tag(name = "Project management", description = "API'S for managing projects")
public class ProjectController {
    private final ProjectService projectService;

    @Operation(summary = "Create project", description = "Create new project in system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = ProjectDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok(projectService.createProject(projectDTO));
    }

    @Operation(summary = "Get project by id", description = "Get current project by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = ProjectDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @Operation(summary = "Update project", description = "Update current project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = ProjectDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok(projectService.updateProject(id, projectDTO));
    }

    @Operation(summary = "Delete project", description = "Delete project on system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = ProjectDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove user in project", description = "Remove current user in project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = ProjectDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @DeleteMapping("/remove/{projectId}/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ProjectDTO> removeUserOnProject(@PathVariable Long projectId, @PathVariable Long userId) {
        return ResponseEntity.ok(projectService.removeUserToProject(projectId, userId));
    }

    @Operation(summary = "Add new user to project", description = "Add new user in to current project in system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = ProjectDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping("/add/{projectId}/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectDTO> addUserToProject(@PathVariable Long projectId, @PathVariable Long userId) {
        return ResponseEntity.ok(projectService.addUserToProject(projectId, userId));
    }

    @Operation(summary = "Get all project", description = "Get all project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = ProjectDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }
}
