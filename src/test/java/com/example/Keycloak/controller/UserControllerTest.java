package com.example.Keycloak.controller;

import com.example.Keycloak.DTO.UserDTO;
import com.example.Keycloak.model.USER_ROLE;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void registerUserTest() throws Exception {
        UserDTO userDTO = new UserDTO(null, "TestUser", "User@123", USER_ROLE.ADMIN, null, null);

        var json = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("TestUser"))
                .andExpect(jsonPath("$.email").value("User@123"))
                .andExpect(jsonPath("$.taskId").isEmpty())
                .andExpect(jsonPath("$.projectId").isEmpty())
                .andReturn();
    }

    @Test
    public void getUserByIdTest() throws Exception {
        Long userID = createUserTest("TestUser", "User@123", USER_ROLE.ADMIN);

        mockMvc.perform(get("/users/" + userID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("TestUser"))
                .andExpect(jsonPath("$.email").value("User@123"))
                .andExpect(jsonPath("$.taskId").isEmpty())
                .andExpect(jsonPath("$.projectId").isEmpty())
                .andReturn();

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
