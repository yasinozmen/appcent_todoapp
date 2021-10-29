package com.AppcentTodoApp.controller;

import com.AppcentTodoApp.config.jwt.JwtUtil;
import com.AppcentTodoApp.model.User;
import com.AppcentTodoApp.payload.request.LoginForm;
import com.AppcentTodoApp.repository.UserRepo;
import com.AppcentTodoApp.dao.UserDAO;
import com.AppcentTodoApp.payload.request.SignUpForm;
import com.AppcentTodoApp.payload.response.LoginResponse;
import com.AppcentTodoApp.payload.response.Response;

import com.AppcentTodoApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    UserRepo userRepo;



    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserService userService;



    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@Valid @RequestBody SignUpForm signUpForm){
        // Response object
        Response response = new Response();

        // Check the email and username in the signupForm
        if(userRepo.existsByUsername(signUpForm.getUsername())){
            response.setMessage("Error: Username is already taken");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        if(userRepo.existsByEmail(signUpForm.getEmail())){
            response.setMessage("Error: Email is already taken");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        //Create a new user
         User user = new User(
                 signUpForm.getUsername(),
                 encoder.encode(signUpForm.getPassword()),
                 signUpForm.getFirstName(),
                 signUpForm.getLastName(),
                 signUpForm.getEmail()
                 );









        userRepo.save(user);
        response.setMessage("user Registered successfuly");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginForm loginForm){

        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(),
                                                                    loginForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user =  (User) authentication.getPrincipal();

        String jwt = jwtUtil.generateToken(authentication);

        UserDAO userDAO = userService.getUserDAO(user);
        return  ResponseEntity.ok( new LoginResponse(userDAO,jwt));
    }
}
