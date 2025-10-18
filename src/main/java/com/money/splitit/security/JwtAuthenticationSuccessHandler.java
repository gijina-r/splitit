package com.money.splitit.security;
import com.money.splitit.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class JwtAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    @Autowired
    private JwtService jwtService;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        return Mono.justOrEmpty(authentication.getPrincipal())
                .cast(DefaultOAuth2User.class)  // use DefaultOAuth2User
                .flatMap(principal -> {
                    String email = principal.getAttribute("email"); // email attribute from Google
                    if (email == null) {
                        return Mono.error(new IllegalStateException("Email is null"));
                    }

                    String jwt = jwtService.generateToken(email);

                    var response = webFilterExchange.getExchange().getResponse();
                    response.getHeaders().add("X-SPLITIT-JWT", jwt);
                    response.setStatusCode(HttpStatus.FOUND);
                    response.getHeaders().setLocation(URI.create("/home"));

                    return response.setComplete();
                })
                .onErrorResume(e -> {
                    // Redirect to login on error
                    var response = webFilterExchange.getExchange().getResponse();
                    response.setStatusCode(HttpStatus.FOUND);
                    response.getHeaders().setLocation(URI.create("/login"));
                    return response.setComplete();
                });
    }

}

