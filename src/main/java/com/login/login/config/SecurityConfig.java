package com.login.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
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
        .csrf(csrf -> csrf
            .ignoringRequestMatchers("/login", "/logout")) //ignorere csrf för login/logout
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/", "/register", "/login", "/product/**").permitAll()  // Tillåt öppna sidor
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
        .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Skapa session om det behövs
        .invalidSessionUrl("/login") // Om sessionen är ogiltig, omdirigera till login-sidan
        )   
        .exceptionHandling(exception -> exception
            .accessDeniedPage("/") // Obehörig åtkomst omdirigeras till startsidan
        );
     
    return http.build();
}


    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userService.loadUserByUsername(username);  // Använder metod i UserService
    }
    
}



