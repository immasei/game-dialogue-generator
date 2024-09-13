package com.example.game_dialogue_generator.config;

import com.example.game_dialogue_generator.repository.UserRepository;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserRepository userRepository;
    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/login", "/signup", "/users", "/js/**", "/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .securityContext((context) -> context.securityContextRepository(securityContextRepository))
                .sessionManagement((session) -> {
                            session.maximumSessions(1).maxSessionsPreventsLogin(true);
                            session.sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession);
                            session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                        }
                )
                .formLogin((form) -> form
                        .loginPage("/")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout((logout) -> {
                            logout.logoutUrl("/logout");
                            logout.addLogoutHandler(
                                    new HeaderWriterLogoutHandler(
                                            new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.COOKIES)
                                    )
                            );
                            logout.deleteCookies("JSESSIONID");
                        }
                )
                .authenticationProvider(authenticationProvider());


        return http.build();
    }


}
