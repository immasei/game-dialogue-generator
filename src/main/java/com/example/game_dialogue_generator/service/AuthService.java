package com.example.game_dialogue_generator.service;


import com.example.game_dialogue_generator.dto.UserDTO;
import com.example.game_dialogue_generator.model.User;
import com.example.game_dialogue_generator.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Repository: raw crud between db and server - limited method
// Service: customise method ie login - match by bcrypt
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcrypt;

//    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bcrypt;
//
//    @Autowired
//    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bcrypt) {
//        this.userRepository = userRepository;
//        this.bcrypt = bcrypt;
//    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll(); // Fetch all users from the repository
    }

    public Optional<User> signup(UserDTO user) {
        // duplicate
        Optional<User> activeUser = userRepository.findByUsername(user.getUsername());
        if (activeUser.isPresent()) return null;

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
        return activeUser;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Load user by username
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//        return user;
//    }
}
