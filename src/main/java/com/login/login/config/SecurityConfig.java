package com.login.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.login.login.service.UserService;  // Importera UserService för att hämta användare från databasen

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;  // Injekt UserService för att hämta användardata från databas

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/", "/register", "/login").permitAll()  // Tillåt öppna sidor
            .requestMatchers("/orders").authenticated()  // Endast inloggade användare kan se mina ordrar
            .anyRequest().authenticated()  // Andra sidor kräver inloggning
        )
        .formLogin(form -> form
            .loginPage("/login")
            .permitAll() // Tillåt åtkomst till login-sidan för alla
            .defaultSuccessUrl("/", true) // Efter inloggning omdirigeras användaren till startsidan
        )
        .logout(logout -> logout
            .permitAll() // Tillåt logout för alla
            .logoutSuccessUrl("/") // Efter utloggning omdirigeras användaren till startsidan
        )
        .exceptionHandling(exception -> exception
            .accessDeniedPage("/") // Obehörig åtkomst omdirigeras till startsidan
        );
    
    return http.build();
}

    /* 
    @Bean
    public UserDetailsService userDetailsService() {
        // använder en custom UserDetailsService för att hämta användare från databasen
        return username -> userService.findByUserName(username)
            .map(user -> org.springframework.security.core.userdetails.User
                    .withUsername(user.getUserName())
                    .password(user.getPassword())
                    .roles("USER")
                    .build())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    } */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userService.loadUserByUsername(username);  // Använd den metod vi just skrev i UserService
    }
    
}



