package com.example.game_dialogue_generator.service;


import com.example.game_dialogue_generator.dto.UserDTO;
import com.example.game_dialogue_generator.model.User;
import com.example.game_dialogue_generator.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcrypt;

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll(); // Fetch all users from the repository
    }

    public Optional<User> getUserById(Integer userid) {
        return userRepository.findById(userid);
    }

    public Optional<User> signup(UserDTO user) {
        // duplicate
        Optional<User> activeUser = userRepository.findByUsername(user.getUsername());
        if (activeUser.isPresent()) return Optional.empty();

        // new user
        User newUser = new User();
        // hash pwd
        String hashedPassword = bcrypt.encode(user.getPassword());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(hashedPassword);
        userRepository.save(newUser);

        return userRepository.findByUsername(newUser.getUsername());
    }

    public Optional<User> login(UserDTO user) {
        Optional<User> activeUser = userRepository.findByUsername(user.getUsername());
        if (activeUser.isPresent()) {
            String expected = activeUser.get().getPassword();
            String actual = user.getPassword();

            if (bcrypt.matches(actual, expected))
                return userRepository.findByUsername(user.getUsername());
        }
        return Optional.empty();
    }
}
