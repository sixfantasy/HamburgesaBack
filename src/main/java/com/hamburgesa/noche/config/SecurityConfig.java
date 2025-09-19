package com.hamburgesa.noche.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Value("${app.local-domain-front}")
    private String localDomainFront;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    // ✅ CHANGE 1: Define the PasswordEncoder bean
    // This method creates the BCryptPasswordEncoder bean, making it available
    // for dependency injection throughout the application.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ CHANGE 2: Define the AuthenticationManager bean correctly
    // This is the modern way to get the AuthenticationManager bean.
    // The previous method was more complex and less standard.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF since we are using JWT (stateless)
                .csrf(csrf -> csrf.disable())
                .cors(withDefaults())
                // Define authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/endpointdesprotegido", "/authenticate").permitAll() // Permit access to specific endpoints
                        .anyRequest().authenticated() // All other requests require authentication
                )
                // Configure stateless session management, essential for REST APIs with JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Add the custom JWT filter before the standard username/password authentication filter
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        // httpBasic is not typically needed for a JWT-based API
        // .httpBasic(withDefaults());

        return http.build();
    }

    // ✅ CHANGE 4: Simplified CORS Configuration
    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Chained methods for better readability
                registry.addMapping("/**")
                        .allowedOrigins(localDomainFront)
                        .allowedMethods("POST", "PUT", "GET", "DELETE", "OPTIONS");
            }
        };
    }
}