package com.thulasimani.product_management.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf->csrf.disable())
                .cors(cors->cors.configurationSource(request -> {
                    CorsConfiguration configuration=new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("http://localhost:3000"));
                    configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
                    configuration.setAllowedHeaders(List.of("*"));
                    configuration.setAllowCredentials(true);
                    return configuration;
                }))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception->exception.authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .authorizeHttpRequests(auth->auth.requestMatchers(
                        "/api/v1/auth/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**"
                ).permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/v1/products/**")
                                .hasAnyRole("USER","ADMIN")
                                .requestMatchers(HttpMethod.POST,"/api/v1/products/**")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/api/v1/products/**")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/products/**")
                                .hasRole("ADMIN")
                                .anyRequest().authenticated()

                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

}
