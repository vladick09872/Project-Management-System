package com.example.Keycloak.controller;

import com.example.Keycloak.DTO.ProjectDTO;
import com.example.Keycloak.DTO.TaskDTO;
import com.example.Keycloak.model.Status;
import com.example.Keycloak.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    ;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "adad", roles = {"DEVELOPER", "MANAGER"})
    public void createTask() throws Exception {
        TaskDTO taskDTO = new TaskDTO(null, "Task1", "this is task 1", Status.DONE, null, null);

        Long projectId = createProjectTest("Test Project", "Test Project Description");

        var json = objectMapper.writeValueAsString(taskDTO);

        mockMvc.perform(post("/tasks/create/" + projectId + "/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Task1"))
                .andExpect(jsonPath("$.description").value("this is task 1"))
                .andExpect(jsonPath("$.assigneeId").isEmpty())
                .andExpect(jsonPath("$.projectId").isEmpty())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "adad", roles = {"ADMIN"})
    public void getTaskById() throws Exception {
        Long taskId = createTaskTest("Task1", "this is task 1", Status.DONE);

        mockMvc.perform(get("/tasks/" + taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Task1"))
                .andExpect(jsonPath("$.description").value("this is task 1"))
                .andExpect(jsonPath("$.assigneeId").isEmpty())
                .andExpect(jsonPath("$.projectId").isEmpty())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "adad", roles = {"ADMIN", "MANAGER"})
    public void updateTaskTest() throws Exception {
        Long taskId = createTaskTest("Task1", "this is task 1", Status.DONE);

        TaskDTO updatedTask = new TaskDTO(null, "Task2", "this is task 2", Status.NEW, null, null);

        var json = objectMapper.writeValueAsString(updatedTask);

        mockMvc.perform(put("/tasks/update/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Task2"))
                .andExpect(jsonPath("$.description").value("this is task 2"))
                .andExpect(jsonPath("$.status").value(Status.NEW.toString()))
                .andExpect(jsonPath("$.assigneeId").isEmpty())
                .andExpect(jsonPath("$.projectId").isEmpty())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "adad", roles = {"ADMIN", "MANAGER"})
    public void deleteTaskTest() throws Exception {
        Long taskId = createTaskTest("Task1", "this is task 1", Status.NEW);

        mockMvc.perform(delete("/tasks/delete/" + taskId))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "adad", roles = {"ADMIN", "MANAGER"})
    public void changeTaskStatusTest() throws Exception {
        Long taskId = createTaskTest("Task1", "this is task 1", Status.NEW);

        TaskDTO updatedTaskStatus = new TaskDTO(null, "Task2", "this is task 2", Status.NEW, null, null);

        var json = objectMapper.writeValueAsString(updatedTaskStatus);

        mockMvc.perform(put("/tasks/status/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Status.NEW.toString()))
                .andExpect(jsonPath("$.assigneeId").isEmpty())
                .andExpect(jsonPath("$.projectId").isEmpty())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "adad", roles = {"ADMIN", "MANAGER"})
    public void getAllTaskTest() throws Exception {
        createTaskTest("Task1", "this is task 1", Status.NEW);
        createTaskTest("Task2", "this is task 2", Status.NEW);
        createTaskTest("Task3", "this is task 3", Status.NEW);

        mockMvc.perform(get("/tasks/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].title").value("Task1"))
                .andExpect(jsonPath("$[1].title").value("Task2"))
                .andExpect(jsonPath("$[2].title").value("Task3"))
                .andExpect(jsonPath("$[0].description").value("this is task 1"))
                .andExpect(jsonPath("$[1].description").value("this is task 2"))
                .andExpect(jsonPath("$[2].description").value("this is task 3"))
                .andReturn();

    }

    public Long createTaskTest(String title, String description, Status status) throws Exception {
        TaskDTO taskDTO = new TaskDTO(null, title, description, status, null, null);

        Long projectId = createProjectTest("Test Project", "Test Project Description");

        var json = objectMapper.writeValueAsString(taskDTO);

        var res = mockMvc.perform(post("/tasks/create/" + projectId + "/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        var created = objectMapper.readValue(res.getResponse().getContentAsString(), TaskDTO.class);

        return created.id();
    }

    public Long createProjectTest(String name, String description) throws Exception {
        ProjectDTO projectDTO = new ProjectDTO(null, name, description, null, null);

        var json = objectMapper.writeValueAsString(projectDTO);

        var result = mockMvc.perform(post("/projects/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        var created = objectMapper.readValue(result.getResponse().getContentAsString(), ProjectDTO.class);

        return created.id();
    }
}
