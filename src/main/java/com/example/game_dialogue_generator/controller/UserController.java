package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.dto.UserDTO;
import com.example.game_dialogue_generator.handler.ResponseHandler;
import com.example.game_dialogue_generator.model.User;
import com.example.game_dialogue_generator.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// @Valid UserDTO: valid the param from request body using UserDTO user
//                 if it doesnt match the DTO, throw Exception (see Exception handler)

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseHandler.handle(HttpStatus.OK, "List of all users", users);
    }

    @GetMapping("/users/{userid}")
    public ResponseEntity<?> getUser(@PathVariable Integer userid) {
        Optional<User> user = userService.getUserById(userid);

        if (user.isPresent())
            return ResponseHandler.handle(HttpStatus.OK, "User found", userid);
        return ResponseHandler.handle(HttpStatus.NOT_FOUND, "No user found", null);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserDTO user) {
        Optional<User> newUser = userService.signup(user);

        if (newUser.isPresent())
            return ResponseHandler.handle(HttpStatus.CREATED, "Signup OK", newUser.get());
        return ResponseHandler.handle(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to signup", null);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDTO user) {
        Optional<User> activeUser = userService.login(user);

        if (activeUser.isPresent())
            return ResponseHandler.handle(HttpStatus.OK, "Login OK", activeUser.get());
        return ResponseHandler.handle(HttpStatus.NOT_FOUND, "Unable to login", null);
    }
}
