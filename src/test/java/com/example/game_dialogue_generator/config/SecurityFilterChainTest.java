package com.example.game_dialogue_generator.config;

import com.example.game_dialogue_generator.controller.AuthController;
import com.example.game_dialogue_generator.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SecurityFilterChainTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthController authController;
//
//    @Test
//    void testUser_LoginLogoutPage() throws Exception {
//        User user = new User();
//        user.setUsername("username");
//        user.setPassword("password");
//        AuthService authService = mock(AuthService.class);
//        when(authService.login(any()))
//                .thenReturn(Optional.of(user));
//
////        Authentication authentication = mock(Authentication.class);
////        SecurityContext securityContext = mock(SecurityContext.class);
////        doNothing().when(securityContext).setAuthentication(any());
////        doNothing().when(securityContextRepository).saveContext(any(), any(), any());
////        when(securityContextHolderStrategy.getContext()).thenReturn(securityContext);
////        when(authenticationManager.authenticate(any())).thenReturn(authentication);
//
////        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
//        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
//                .unauthenticated(
//                        user.getUsername(), user.getPassword()
//                );
//        when(authenticationManager.authenticate(any()))
//                .thenReturn(token);
//
//
//        mockMvc.perform(post("/login")
//                        .content("{\"username\": \"username\", \"password\": \"password\"}")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }

    @Test
    void testUserAnonymous_indexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth"));
    }
}
