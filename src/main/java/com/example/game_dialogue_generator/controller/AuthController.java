package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.dto.UserDTO;
import com.example.game_dialogue_generator.handler.ResponseHandler;
import com.example.game_dialogue_generator.model.User;
import com.example.game_dialogue_generator.service.AuthService;
import org.springframework.security.core.context.SecurityContextHolderStrategy;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// @Valid UserDTO: valid the param from request body using UserDTO user
//                 if it doesnt match the DTO, throw Exception (see Exception handler)
@RestController
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    AuthenticationManager authenticationManager;

    private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();


    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        // this route is used for -get- testing
        // will be removed in the future
        List<User> users = authService.getAllUsers();
        return ResponseHandler.handle(HttpStatus.OK, "List of all users", users);
    }

    @GetMapping("/username")
    public ResponseEntity<?> getUsername() {
        User principle = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principle);
        System.out.println(principle.getUsername());
        return ResponseHandler.handle(HttpStatus.OK, "username", principle.getUsername());
    }

    @GetMapping("/userid")
    public ResponseEntity<?> getUserId() {
        User principle = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principle.getUsername());
        return ResponseHandler.handle(HttpStatus.OK, "userid", principle.getUserid());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserDTO user, HttpServletRequest request, HttpServletResponse response) {
        Optional<User> newUser = authService.signup(user);

        if (newUser.isPresent()) {
            try {
                UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
                        .unauthenticated(
                                newUser.get().getUsername(), user.getPassword()
                        );
                Authentication authentication = authenticationManager.authenticate(token);
                SecurityContext context = securityContextHolderStrategy.createEmptyContext();
                context.setAuthentication(authentication);
                securityContextHolderStrategy.setContext(context);
                securityContextRepository.saveContext(context, request, response);

                System.out.println("Authentication successful.");
            } catch (AuthenticationException e) {
                System.out.println("Authentication failed: " + e.getMessage());
            }
            return ResponseHandler.handle(HttpStatus.CREATED, "Signup OK", newUser);
        }

        return ResponseHandler.handle(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to signup", null);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDTO user, HttpServletRequest request, HttpServletResponse response) {
        Optional<User> activeUser = authService.login(user);

        if (activeUser.isPresent()) {
            try {
                UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
                        .unauthenticated(
                                activeUser.get().getUsername(), user.getPassword()
                        );
                Authentication authentication = authenticationManager.authenticate(token);
                SecurityContext context = securityContextHolderStrategy.createEmptyContext();
                context.setAuthentication(authentication);
                securityContextHolderStrategy.setContext(context);
                securityContextRepository.saveContext(context, request, response);

                System.out.println("Authentication OK.");

            } catch (AuthenticationException e) {
                System.out.println("Authentication Failed: " + e.getMessage());
            }
            return ResponseHandler.handle(HttpStatus.OK, "Login OK", activeUser);
        }

        return ResponseHandler.handle(HttpStatus.NOT_FOUND, "Unable to login", null);
    }
    //
}
