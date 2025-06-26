package com.example.Keycloak.config;

import com.example.Keycloak.converter.CustomJwtConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.PostMapping;

import java.nio.file.attribute.PosixFileAttributes;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/", "/v3/api-docs/", "/swagger-ui.html",
                                "/swagger-resources/", "/webjars/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/current").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/users/all/couriers").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/products/all").hasRole("COURIER")
                        .requestMatchers(HttpMethod.GET, "/products/**").hasAnyRole("MANAGER", "COURIER")
                        .requestMatchers(HttpMethod.PUT, "/products/update/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/products/create").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/products/delete/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/orders/create").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/orders/**").hasRole("COURIER")
                        .requestMatchers(HttpMethod.GET, "/orders/all").hasRole("COURIER")
                        .requestMatchers(HttpMethod.PUT, "/orders/update/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/orders/{orderId}/cancel/{username}").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/order-items/**").hasRole("COURIER")
                        .requestMatchers(HttpMethod.POST, "/deliveries/assign/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/deliveries/all").hasRole("COURIER")
                        .requestMatchers(HttpMethod.PUT, "/deliveries/updateStatus/**").hasRole("COURIER")
                        .requestMatchers(HttpMethod.GET, "/deliveries/**").hasAnyRole("MANAGER", "COURIER")
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
