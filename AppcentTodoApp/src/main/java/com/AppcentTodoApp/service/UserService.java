package com.AppcentTodoApp.service;

import com.AppcentTodoApp.dao.UserDAO;
import com.AppcentTodoApp.model.User;

import java.util.List;

public interface UserService {
    UserDAO getUserDAO(User user);
    UserDAO getUserDAOByName(String username);
    List<UserDAO> getAllUsers();



}
