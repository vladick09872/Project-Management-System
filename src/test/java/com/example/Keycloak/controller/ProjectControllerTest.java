package com.example.Keycloak.controller;

import com.example.Keycloak.DTO.ProjectDTO;
import com.example.Keycloak.DTO.UserDTO;
import com.example.Keycloak.model.USER_ROLE;
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
public class ProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "Test User", roles = {"ADMIN", "MANAGER"})
    public void createProject() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO(null, "Test", "Test", null, null);

        var json = objectMapper.writeValueAsString(projectDTO);

        mockMvc.perform(post("/projects/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.description").value("Test"))
                .andExpect(jsonPath("$.taskId").isEmpty())
                .andExpect(jsonPath("$.ownerId").isEmpty())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "Test User", roles = {"ADMIN"})
    public void getProjectById() throws Exception {
        Long projectId = createProjectTest("Test User", "User123");

        mockMvc.perform(get("/projects/" + projectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.description").value("User123"))
                .andExpect(jsonPath("$.ownerId").isEmpty())
                .andExpect(jsonPath("$.taskId").isEmpty())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "Test User", roles = {"ADMIN"})
    public void updateProject() throws Exception {
        Long projectId = createProjectTest("Test User", "User123");

        ProjectDTO updateProject = new ProjectDTO(null, "Test", "Test", null, null);

        var json = objectMapper.writeValueAsString(updateProject);

        mockMvc.perform(put("/projects/update/" + projectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.description").value("Test"))
                .andExpect(jsonPath("$.ownerId").isEmpty())
                .andExpect(jsonPath("$.taskId").isEmpty())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "Test User", roles = "ADMIN")
    public void deleteProject() throws Exception {
        Long projectId = createProjectTest("Test User", "123");

        mockMvc.perform(delete("/projects/delete/" + projectId))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "Test User", roles = {"ADMIN"})
    public void addUserToProject() throws Exception {
        Long userId = createUserTest("User", "User123", USER_ROLE.DEVELOPER);
        Long projectId = createProjectTest("TestUser", "AAAA");

        mockMvc.perform(post("/projects/add/" + projectId + "/user/"  + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("TestUser"))
                .andExpect(jsonPath("$.description").value("AAAA"))
                .andExpect(jsonPath("$.ownerId").isEmpty())
                .andExpect(jsonPath("$.taskId").isEmpty())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "Test User", roles = {"ADMIN", "MANAGER"})
    public void removeUserFromProject() throws Exception {
        Long userId = createUserTest("User", "User123", USER_ROLE.DEVELOPER);
        Long projectId = createProjectTest("TestUser", "AAAA");

        mockMvc.perform(delete("/projects/remove/" + projectId + "/user/"  + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestUser"))
                .andExpect(jsonPath("$.description").value("AAAA"))
                .andExpect(jsonPath("$.ownerId").isEmpty())
                .andExpect(jsonPath("$.taskId").isEmpty())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "aadad", roles = {"ADMIN"})
    public void getAllProjects() throws Exception {
        createProjectTest("Test user1", "1231");
        createProjectTest("Test user2", "1232");
        createProjectTest("Test user3", "1233");

        mockMvc.perform(get("/projects/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value("Test user1"))
                .andExpect(jsonPath("$[1].name").value("Test user2"))
                .andExpect(jsonPath("$[2].name").value("Test user3"))
                .andExpect(jsonPath("$[0].description").value("1231"))
                .andExpect(jsonPath("$[1].description").value("1232"))
                .andExpect(jsonPath("$[2].description").value("1233"))
                .andReturn();
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

    public Long createUserTest(String username, String email, USER_ROLE role) throws Exception {
        var userDTO = new UserDTO(null, username, email, role, null, null);

        var json = objectMapper.writeValueAsString(userDTO);

        var result = mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        var created = objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class);

        return created.id();
    }
}
