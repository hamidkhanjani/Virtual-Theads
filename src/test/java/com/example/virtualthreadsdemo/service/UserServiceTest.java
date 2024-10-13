package com.example.virtualthreadsdemo.service;


import com.example.virtualthreadsdemo.model.User;
import com.example.virtualthreadsdemo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void createUser_shouldSaveAndReturnUser() throws ExecutionException, InterruptedException {
        // Arrange
        User user = new User();
        user.setName("Hamid Khanjani");
        user.setEmail("hamid.kjhanjani@example.com");

        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User savedUser = userService.createUser(user).get();

        // Assert
        assertEquals("Hamid Khanjani", savedUser.getName());
        assertEquals("hamid.kjhanjani@example.com", savedUser.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getUserById_shouldReturnUserWhenExists() throws ExecutionException, InterruptedException {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setName("Hamid Khanjani");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.getUserById(1L).get();

        // Assert
        assertNotNull(foundUser);
        assertEquals("Hamid Khanjani", foundUser.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_shouldReturnNullWhenNotFound() throws ExecutionException, InterruptedException {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        User foundUser = userService.getUserById(1L).get();

        // Assert
        assertNull(foundUser);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void deleteUser_shouldDeleteUserById() throws ExecutionException, InterruptedException {
        // Act
        userService.deleteUserById(1L).get();

        // Assert
        verify(userRepository, times(1)).deleteById(1L);
    }
}
