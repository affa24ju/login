package com.login.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
    //Definerar PasswordEncoder för att hantera krypterade lösenord
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/images/**").permitAll() //tillåter images från static map
                .requestMatchers("/", "/register", "/login", "/product/**").permitAll()  // Tillåt öppna sidor
                .requestMatchers("/orders").authenticated()  // Endast inloggade användare kan se mina ordrar
                .anyRequest().authenticated()  // Andra sidor kräver inloggning
            )
            .userDetailsService(userService)
            .formLogin(form -> form
                .permitAll()
                .defaultSuccessUrl("/", true) // Efter inloggning omdirigeras användaren till startsidan
                .failureUrl("/login?error=true")
            )
            .logout(logout -> logout
                .permitAll() // Tillåt logout för alla
                .invalidateHttpSession(true)
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Skapa session om det behövs
                //.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            )   
            .exceptionHandling(exception -> exception
                .accessDeniedPage("/") // Obehörig åtkomst omdirigeras till startsidan
            );
     
    return http.build();
    }
    //Definerar UserDetailsService för att hämta användar data från databas
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userService.loadUserByUsername(username);  // Använder metod i UserService
    }

}



