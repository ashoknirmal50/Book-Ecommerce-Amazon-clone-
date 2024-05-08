package com.AmazonClone.Seller.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.Customizer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    // Bean for password encoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Bean for user details service
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){    
    
    //Creating admin user with encoded password and roles
    UserDetails admin = User.withUsername("admin")
        .password(encoder.encode("123"))
        .roles("ADMIN","USER").build(); 

    //Creating reagular user with encoded password and role
    UserDetails user = User.withUsername("user")
    .password(encoder.encode("12345"))
    .roles("USER")
    .build();

        // Returning an instance of InMemoryUserDetailsManager with admin and user details
        return new InMemoryUserDetailsManager(admin,user);
    }

    //Bean for security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //Configuring authorization for all HTTP req
        return http.csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> 
        authorizationManagerRequestMatcherRegistry
        .anyRequest()
        .authenticated())
        // Configuring HTTP Basic authentication with default settings
        .httpBasic(Customizer.withDefaults())
        .build();
    }
}