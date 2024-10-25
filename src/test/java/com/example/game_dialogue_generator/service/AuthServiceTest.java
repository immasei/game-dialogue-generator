package com.example.game_dialogue_generator.service;

import com.example.game_dialogue_generator.dto.UserDTO;
import com.example.game_dialogue_generator.model.User;
import com.example.game_dialogue_generator.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bcrypt;

    @InjectMocks
    private AuthService authService;

    @Test
    void testSignup_CreateUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("password");
        User user = new User();
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user);
        when(bcrypt.encode(any())).thenReturn("password");

        Optional<User> result = authService.signup(userDTO);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSignup_UserExists() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("password");
        User user = new User();
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.of(user));

        Optional<User> result = authService.signup(userDTO);

        assertNotNull(result);
        assertFalse(result.isPresent());
    }

    @Test
    void testLogin_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("password");
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.of(user));
        when(bcrypt.matches(any(), any())).thenReturn(true);

        Optional<User> result = authService.login(userDTO);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(user.getUsername(), result.get().getUsername());
    }

    @Test
    void testLogin_UserNotFound() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("password");
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.empty());

        Optional<User> result = authService.login(userDTO);

        assertNotNull(result);
        assertFalse(result.isPresent());
    }

    @Test
    void testLogin_PasswordNotMatch() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("password");
        User user = new User();
        user.setUsername("username");
        user.setPassword("notpassword");
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.of(user));
        when(bcrypt.matches(any(), any())).thenReturn(false);

        Optional<User> result = authService.login(userDTO);

        assertNotNull(result);
        assertFalse(result.isPresent());
    }
}
