package com.example.virtualthreadsdemo.service;

import com.example.virtualthreadsdemo.model.User;
import com.example.virtualthreadsdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ExecutorService virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CompletableFuture<List<User>> getAllUsers() {
        return CompletableFuture.supplyAsync(userRepository::findAll, virtualThreadExecutor);
    }

    public CompletableFuture<User> createUser(User user) {
        return CompletableFuture.supplyAsync(() -> userRepository.save(user), virtualThreadExecutor);
    }

    public CompletableFuture<User> getUserById(Long id) {
        return CompletableFuture.supplyAsync(() -> userRepository.findById(id).orElse(null), virtualThreadExecutor);
    }

    public CompletableFuture<Void> deleteUserById(Long id) {
        return CompletableFuture.runAsync(() -> userRepository.deleteById(id), virtualThreadExecutor);
    }
}