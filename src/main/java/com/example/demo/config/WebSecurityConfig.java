package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    /* jeito antigo
    @Autowired
    private SecurityDatabaseService securityService;

    @Bean
    public UserDetailsService userDetailsService() {
        return securityService;
    }
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/login")
                        .permitAll()

                        .requestMatchers("/managers")
                        .hasRole("ADMIN")

                        .requestMatchers("/users")
                        .hasAnyRole("USER", "ADMIN")

                        .anyRequest()
                        .authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    /* deixou de existir depois de criar: SecurityDatabaseService(config), User(model), UserRepository(repository)
    e add no pom.xml a dependencia data-jpa
    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user = User
                .withUsername("user")
                .password("{noop}user")
                .roles("USER")
                .build();

        UserDetails admin = User
                .withUsername("admin")
                .password("{noop}admin")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
     */
}