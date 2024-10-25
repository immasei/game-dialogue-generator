package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.model.User;
import com.example.game_dialogue_generator.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private SecurityContextHolderStrategy securityContextHolderStrategy;

    @MockBean
    private SecurityContextRepository securityContextRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthController authController;

    @Test
    void testSignup_Success() throws Exception {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        when(authService.signup(any())).thenReturn(Optional.of(user));
        String userJson =  "{\"username\":\"username\",\"password\":\"password\"}";

        doNothing().when(securityContextHolderStrategy).setContext(any());
        doNothing().when(securityContextRepository).saveContext(any(), any(), any());

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        mockMvc.perform(post("/signup")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Signup OK"));
        verify(authService, times(1)).signup(any());
    }

    @Test
    void testSignup_NoNewUser() throws Exception {
        when(authService.signup(any())).thenReturn(Optional.empty());
        String userJson =  "{\"username\":\"username\",\"password\":\"password\"}";

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        mockMvc.perform(post("/signup")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Unable to signup"));
        verify(authService, times(1)).signup(any());
    }

    @Test
    void testSignup_AuthException() throws Exception {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        when(authService.signup(any())).thenReturn(Optional.of(user));
        String userJson =  "{\"username\":\"username\",\"password\":\"password\"}";

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Authentication failed"));

        mockMvc.perform(post("/signup")
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Unable to signup"));
        verify(authService, times(1)).signup(any());
    }

    @Test
    void testLogin_Success() throws Exception {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        when(authService.login(any())).thenReturn(Optional.of(user));
        String userJson =  "{\"username\":\"username\",\"password\":\"password\"}";

        doNothing().when(securityContextHolderStrategy).setContext(any());
        doNothing().when(securityContextRepository).saveContext(any(), any(), any());

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        mockMvc.perform(post("/login")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login OK"));
        verify(authService, times(1)).login(any());
    }

    @Test
    void testLogin_NoActiveUser() throws Exception {
        when(authService.login(any())).thenReturn(Optional.empty());
        String userJson =  "{\"username\":\"username\",\"password\":\"password\"}";

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        mockMvc.perform(post("/login")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Unable to login"));
        verify(authService, times(1)).login(any());
    }

    @Test
    void testLogin_AuthException() throws Exception {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        when(authService.login(any())).thenReturn(Optional.of(user));
        String userJson =  "{\"username\":\"username\",\"password\":\"password\"}";

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Authentication failed"));

        mockMvc.perform(post("/login")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Unable to login"));
        verify(authService, times(1)).login(any());
    }
}
