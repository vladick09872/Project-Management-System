package com.example.Keycloak.config;

import com.example.Keycloak.converter.CustomJwtConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
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
                        .requestMatchers(HttpMethod.POST, "/cars/add").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/cars/all").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/cars/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.PUT, "/cars/update/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/cars/delete/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/customers/register").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/customers/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/cars/all").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/purchases/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/purchases/customer/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/purchases/all").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/testDriveRequest/request/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.PUT, "/testDriveRequest/approve/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/testDriveRequest/decline/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/testDriveRequest/all").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/testDriveRequest/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter())));

        return http.build();
    }

    @Bean
    public Converter<Jwt, ? extends AbstractAuthenticationToken> jwtConverter() {
        var converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(new CustomJwtConverter());

        return converter;
    }
}
