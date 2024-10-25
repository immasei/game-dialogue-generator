package com.example.game_dialogue_generator.config;

import com.example.game_dialogue_generator.model.User;
import com.example.game_dialogue_generator.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConfigTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SecurityConfig securityConfig;

    @Test
    void testUserDetailsService() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));

        UserDetailsService result = securityConfig.userDetailsService();

        assertNotNull(result);
        assertNotNull(result.loadUserByUsername(user.getUsername()));
    }

    @Test
    void testPasswordEncoder() {
        BCryptPasswordEncoder result = securityConfig.passwordEncoder();
        assertNotNull(result);
        assertTrue(result instanceof BCryptPasswordEncoder);
    }

    @Test
    void testAuthenticationManager() throws Exception {
        AuthenticationConfiguration config = mock(AuthenticationConfiguration.class);
        AuthenticationManager manager = mock(AuthenticationManager.class);
        when(config.getAuthenticationManager()).thenReturn(manager);

        AuthenticationManager result = securityConfig.authenticationManager(config);
        assertEquals(manager, result);
    }

    @Test
    void testAuthenticationProvider() {
        mock(UserDetailsService.class);
        mock(BCryptPasswordEncoder.class);

        AuthenticationProvider result = securityConfig.authenticationProvider();
        assertNotNull(result);
    }
}
