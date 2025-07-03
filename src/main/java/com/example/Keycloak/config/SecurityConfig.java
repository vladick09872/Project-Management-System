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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/projects/create").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.GET, "/projects/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/projects/update/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/projects/delete/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/projects/remove/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.POST, "/projects/add/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/projects/all").authenticated()
                        .requestMatchers(HttpMethod.POST, "/tasks/create/{projectId}/task").hasAnyRole("DEVELOPER", "MANAGER")
                        .requestMatchers(HttpMethod.GET, "/tasks/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/tasks/update/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/tasks/delete/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/tasks/status/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.GET, "/tasks/all").authenticated()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer ->
                        httpSecurityOAuth2ResourceServerConfigurer.jwt(
                                jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtConverter())))
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
