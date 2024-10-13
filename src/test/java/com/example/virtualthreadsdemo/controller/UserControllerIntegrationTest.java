package com.example.virtualthreadsdemo.controller;

import com.example.virtualthreadsdemo.model.User;
import com.example.virtualthreadsdemo.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll(); // Clear database before each test
    }

    @Test
    void shouldCreateUserAndReturnIt() throws Exception {
        String userJson = """
        {
            "name": "Hamid Khanjani",
            "email": "hamid.khanjani@example.com"
        }
        """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())  // Expect HTTP 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // Ensure JSON response
                .andExpect(jsonPath("$.name").value("Hamid Khanjani"))  // Check name in JSON response
                .andExpect(jsonPath("$.email").value("hamid.khanjani@example.com"));  // Check email in JSON response
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        // Arrange
        User user1 = new User();
        user1.setName("Hamid Khanjani");
        user1.setEmail("hamid.khanjani@example.com");

        User user2 = new User();
        user2.setName("Hamid Khanjani");
        user2.setEmail("hamid.khanjani@example.com");

        userRepository.save(user1);
        userRepository.save(user2);

        // Act
        ResultActions result = mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Hamid Khanjani")))
                .andExpect(jsonPath("$[1].name", is("Hamid Khanjani")));
    }

    @Test
    void shouldGetUserById() throws Exception {
        // Arrange
        User user = new User();
        user.setName("Hamid Khanjani");
        user.setEmail("hamid.khanjani@example.com");
        user = userRepository.save(user);

        // Act
        ResultActions result = mockMvc.perform(get("/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Hamid Khanjani")))
                .andExpect(jsonPath("$.email", is("hamid.khanjani@example.com")));
    }

    @Test
    void shouldReturn404WhenUserNotFound() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/users/1")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteUserById() throws Exception {
        // Arrange
        User user = new User();
        user.setName("Hamid Khanjani");
        user.setEmail("hamid.khanjani@example.com");
        user = userRepository.save(user);

        // Act
        ResultActions result = mockMvc.perform(delete("/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isOk());
        Assertions.assertFalse(userRepository.findById(user.getId()).isPresent());

    }
}
