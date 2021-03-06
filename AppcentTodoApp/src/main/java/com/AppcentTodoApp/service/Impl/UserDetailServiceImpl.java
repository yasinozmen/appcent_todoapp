package com.AppcentTodoApp.service.Impl;


import com.AppcentTodoApp.dao.UserDAO;
import com.AppcentTodoApp.model.User;
import com.AppcentTodoApp.repository.UserRepo;
import com.AppcentTodoApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class UserDetailServiceImpl implements UserDetailsService, UserService {

    @Autowired
    UserRepo userRepo;



    @Override
    public UserDAO getUserDAO(User user) {
      UserDAO userDAO = new UserDAO();
      userDAO.setUserId(user.getUserId());
      userDAO.setUsername(user.getUsername());
      userDAO.setFirstName(user.getFirstName());
      userDAO.setLastName(user.getLastName());
      userDAO.setEmail(user.getEmail());





        return userDAO;
    }



    @Override
    public UserDAO getUserDAOByName(String username) {
        UserDAO userDAO = null;
        Optional<User> user = userRepo.findByUsername(username);
        if(user.isPresent()){
            userDAO = getUserDAO(user.get());
        }
        return userDAO;
    }

    @Override
    public List<UserDAO> getAllUsers() {
      List <User> users = (List<User>) userRepo.findAll();
      return users.stream().map(this::transformUsers).collect(Collectors.toList());

    }



    public UserDAO transformUsers(User user) {
        UserDAO userDAO = new UserDAO();
        userDAO.setUserId(user.getUserId());
        userDAO.setUsername(user.getUsername());
        userDAO.setFirstName(user.getFirstName());
        userDAO.setLastName(user.getLastName());
        userDAO.setEmail(user.getEmail());
        return userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).orElseThrow( () ->
                new UsernameNotFoundException("Username not found " + username));

        return user;
    }




}