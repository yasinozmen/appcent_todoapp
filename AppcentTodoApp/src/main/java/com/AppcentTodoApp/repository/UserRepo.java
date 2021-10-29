package com.AppcentTodoApp.repository;

import com.AppcentTodoApp.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User,Long> {
     Optional<User> findByUsername(String username);
     Boolean existsByUsername(String username);
     Boolean existsByEmail(String email);
}
