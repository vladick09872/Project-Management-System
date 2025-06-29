package com.example.Keycloak.config;

import io.jsonwebtoken.io.Decoders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/students/all").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/students/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/students/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/students/update/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/students/delete/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/courses/all").permitAll()
                        .requestMatchers(HttpMethod.GET, "/courses/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/courses/create").hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers(HttpMethod.PUT, "/courses/update/**").hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers(HttpMethod.DELETE, "/courses/delete/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/enrollments/enroll/student/{studentId}/to/course/{courseId}").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/enrollments/student/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/enrollments/all").hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers(HttpMethod.DELETE, "/enrollments/cancel/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/enrollments/complete/*").hasRole("INSTRUCTOR")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtConverter())))
                .oauth2Login(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public Converter<Jwt, ? extends AbstractAuthenticationToken> jwtConverter() {
        var converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(new CustomJwtConverter());

        return converter;
    }
}
