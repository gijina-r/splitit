package com.money.splitit.config;

import com.money.splitit.security.JwtAuthenticationFilter;
//import com.money.splitit.security.JwtAuthenticationSuccessHandler;
import com.money.splitit.security.JwtAuthenticationSuccessHandler;
import com.money.splitit.security.JwtServerAuthenticationConverter;
import com.money.splitit.security.JwtTokenProvider;
import com.money.splitit.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtAuthenticationSuccessHandler successHandler;
    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtAuthenticationSuccessHandler successHandler, JwtTokenProvider jwtTokenProvider) {
        this.successHandler = successHandler;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        // Custom JWT Authentication filter
        AuthenticationWebFilter jwtAuthFilter = new AuthenticationWebFilter(jwtReactiveAuthManager());
        jwtAuthFilter.setServerAuthenticationConverter(new JwtServerAuthenticationConverter(jwtTokenProvider));

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/", "/login/**", "/oauth2/**", "/error","/swagger-ui/**").permitAll()
                        .pathMatchers("/api/**").authenticated()
                        .anyExchange().permitAll()
                )
                .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .oauth2Login(oauth2 -> oauth2.authenticationSuccessHandler(successHandler))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }


    @Bean
    @Primary
    public ReactiveAuthenticationManager jwtReactiveAuthManager() {
        return authentication -> Mono.just(authentication);
    }
}

