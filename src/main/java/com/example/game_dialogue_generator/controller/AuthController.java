package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.dto.UserDTO;
import com.example.game_dialogue_generator.handler.ResponseHandler;
import com.example.game_dialogue_generator.model.User;
import com.example.game_dialogue_generator.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// @Valid UserDTO: valid the param from request body using UserDTO user
//                 if it doesnt match the DTO, throw Exception (see Exception handler)

@RestController
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        // this route is used for -get- testing
        // will be removed in the future
        List<User> users = userService.getAllUsers();
        return ResponseHandler.handle(HttpStatus.OK, "List of all users", users);
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

        if (activeUser.isPresent()) {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//
//            // Set the authentication in the SecurityContext
//            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseHandler.handle(HttpStatus.OK, "Login OK", activeUser.get());
        }


        return ResponseHandler.handle(HttpStatus.NOT_FOUND, "Unable to login", null);
    }
}
