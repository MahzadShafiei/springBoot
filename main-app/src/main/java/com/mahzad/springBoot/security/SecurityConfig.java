package com.mahzad.springBoot.security;

import jakarta.annotation.PostConstruct;
import org.hibernate.annotations.ConcreteProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

@Bean
SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth ->
            auth.requestMatchers("/users/hello").permitAll()
                    .anyRequest().authenticated()
    )
            .httpBasic(httpBasic -> {});
    return http.build();
}
}
