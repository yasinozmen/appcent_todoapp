package com.AppcentTodoApp.payload.response;

import com.AppcentTodoApp.dao.UserDAO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private UserDAO user;
    private String jwt;
}
