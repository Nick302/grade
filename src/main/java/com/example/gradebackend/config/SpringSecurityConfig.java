package com.example.gradebackend.config;

import com.example.gradebackend.converter.KCRoleConverter;
import com.example.gradebackend.exceptions.OAuth2ExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.example.gradebackend.util.Constant.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@EnableAutoConfiguration
@EnableScheduling
public class SpringSecurityConfig {

    @Value("${client.url}")
    private String clientURL;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KCRoleConverter());

        http.authorizeHttpRequests()
                .requestMatchers(API_AUTH_SECURITY, API_DOCS, URL_SWAGGER).permitAll()
                .requestMatchers(API_EMPLOYEE_SECURITY, API_TASK_SECURITY).hasRole("user")
                .requestMatchers(API_SALARY_SECURITY, API_DEPARTMENT_SECURITY, API_PREMIUM_SECURITY, "/api/v1/report/**").hasRole("admin")
                .anyRequest().authenticated()

                .and()
                .csrf().disable()
                .cors()

                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter)

                .and()
                .authenticationEntryPoint(new OAuth2ExceptionHandler());

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(clientURL));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
