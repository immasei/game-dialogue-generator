package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.dto.UserDTO;
import com.example.game_dialogue_generator.model.User;
import com.example.game_dialogue_generator.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{userid}")
    public ResponseEntity<?> getUser(@PathVariable Integer userid) {
        Optional<User> user = userService.getUserById(userid);

        if (user.isPresent())
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        return new ResponseEntity<>("No user found", HttpStatus.NOT_FOUND);

    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserDTO user) {
        Optional<User> newUser = userService.signup(user);

        if (newUser.isPresent())
            return new ResponseEntity<>(newUser.get(),HttpStatus.CREATED);
        return new ResponseEntity<>("Unable to signup", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDTO user) {
        Optional<User> activeUser = userService.login(user);

        if (activeUser.isPresent())
            return new ResponseEntity<>(activeUser.get(),HttpStatus.OK);
        return new ResponseEntity<>("Unable to login", HttpStatus.NOT_FOUND);
    }
}
