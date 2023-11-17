package com.example.autentificare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ProjectConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        final UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        final UserDetails userDetails1 = User.withUsername("Maria")
                .password(passwordEncoder().encode("1234"))
                .authorities("READ_AUTHORITY")
                .build();

        final UserDetails userDetails2 = User.withUsername("Andrei")
                .password(passwordEncoder().encode("1234"))
                .authorities("READ_AUTHORITY", "WRITE_AUTHORITY", "UPDATE_AUTHORITY")
                .build();

        final UserDetails userDetails3 = User.withUsername("Laura")
                .password(passwordEncoder().encode("1234"))
                .authorities("READ_AUTHORITY", "WRITE_AUTHORITY", "UPDATE_AUTHORITY", "DELETE_AUTHORITY")
                .build();

        userDetailsManager.createUser(userDetails1);
        userDetailsManager.createUser(userDetails2);
        userDetailsManager.createUser(userDetails3);

        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/resources/delete").hasAuthority("DELETE_AUTHORITY")
                        .requestMatchers("/resources/writeOrUpdate").hasAnyAuthority("WRITE_AUTHORITY", "UPDATE_AUTHORITY")
                        .requestMatchers("/resources/welcome").authenticated()

                        .anyRequest().denyAll())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }



    }
