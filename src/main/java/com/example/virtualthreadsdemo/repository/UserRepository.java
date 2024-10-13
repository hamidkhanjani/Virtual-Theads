package com.example.virtualthreadsdemo.repository;

import com.example.virtualthreadsdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
