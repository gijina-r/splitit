package com.money.splitit.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {


    private static String jwtsecret;

    public JwtUtil(@Value("${jwt.secret}") String jwtSecret) {
        this.jwtsecret = jwtSecret;
    }
    private static final long EXPIRATION = 3600_000; // 1 hour



    private static SecretKey getSigningKey() {
        if (jwtsecret.matches("^[A-Za-z0-9+/_=-]+$") && jwtsecret.length() > 40) {
            // Base64-encoded secret
            System.out.println("******************************************** secretttttt");
            return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtsecret));
        } else {
            // Plain string secret (must be >= 32 chars)
           // Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecret));
            System.out.println("******************************************** secretttttt");
            return Keys.hmacShaKeyFor(jwtsecret.getBytes(StandardCharsets.UTF_8));
        }
    }

    public static String generateToken(String email) {
        System.out.println("email::"+email);
        System.out.println("jwtseeeeeeee::"+jwtsecret);

        SecretKey key =  Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtsecret));
        System.out.println("key:::::::"+key);

// Optional: print Base64 key to use in env or application.yml
        //String base64Key = Encoders.BASE64.encode(key.getEncoded());
       // System.out.println("Use this as JWT_SECRET: " + base64Key);
        //SecretKey key = Keys.hmacShaKeyFor(jwtsecret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, jwtsecret)
                .compact();
    }

    public static String validateTokenAndGetSubject(String token) {
        try {
            System.out.println("Token validated successfully for: " + Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject());

            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            System.out.println("JWT validation failed: " + e.getMessage());
        }
        return " ";
    }
}
