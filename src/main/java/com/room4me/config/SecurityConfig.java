package com.room4me.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.room4me.filters.AuthorizationFilter;
import com.room4me.filters.SessionsFilter;
import com.room4me.services.AuthenticationServices;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private AuthorizationFilter authorizationFilter;

    @Autowired
    private AuthenticationServices authenticationServices;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
            .getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(
            authenticationServices
        ).passwordEncoder(passwordEncoder());

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
        .cors().and().csrf().disable()
        .authorizeHttpRequests()
        .requestMatchers(HttpMethod.GET).permitAll()
        .requestMatchers(HttpMethod.POST, "/user**").permitAll()
        .anyRequest().authenticated().and()
        .addFilterBefore(
            new SessionsFilter(
                "/user/sessions", authenticationManager
            ), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
        .authenticationManager(authenticationManager)
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().disable();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(
            Arrays.asList("GET","POST","PUT","PATCH","DELETE")
        );
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}