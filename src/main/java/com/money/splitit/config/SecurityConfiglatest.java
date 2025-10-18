/*package com.money.splitit.config;

import com.money.splitit.filter.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableWebSecurity
public class SecurityConfiglatest {
    private final JwtAuthFilter jwtAuthFilter;
    public SecurityConfiglatest(JwtAuthFilter jwtAuthenticationFilter) {
        this.jwtAuthFilter = jwtAuthenticationFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ClientRegistrationRepository clientRegistrationRepository) throws Exception {
//        http
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/public/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .oauth2Login(oauth2 -> oauth2
//                        .defaultSuccessUrl("/api/home", true)
//                )
//                .csrf(csrf -> csrf.disable());
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/home", true)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/").permitAll()
                )
//                .csrf(csrf -> csrf.disable());
.oauth2Login(Customizer.withDefaults()) // Google OAuth login
                .oauth2Client(Customizer.withDefaults());

        // Add JWT validation filter before processing requests
//        http.addFilterBefore(jwtAuthFilter,
//                org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
            http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        // Optional: OIDC logout handling
//        OidcClientInitiatedLogoutSuccessHandler logoutSuccessHandler =
//                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
//        http.logout(logout -> logout.logoutSuccessHandler(logoutSuccessHandler));
//
//        return http.build();
        return http.build();

    }
}*/

