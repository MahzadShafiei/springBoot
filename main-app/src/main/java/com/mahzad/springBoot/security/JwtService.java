
package com.mahzad.springBoot.security;

import com.mahzad.springBoot.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expirationMs;
    private final UserRepository  userRepository;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms}") long expirationMs,
            UserRepository userRepository) {

        this.secretKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );

        this.expirationMs = expirationMs;
        this.userRepository = userRepository;
    }

    public String generateToken(String username, String tokenType) {
        var user = userRepository.getByUsernameByJpql(username)
                .orElseThrow(() ->
                        new IllegalArgumentException("Username " + username + " not found."));

        Date now = new Date();

        long tokenExpiration = tokenType.equals("PASSWORD_CHANGE")
                ? 5*60*1000L : expirationMs;

        Date expiry = new Date(
                now.getTime() + tokenExpiration
        );

        return Jwts.builder()
                .subject(username)
                .claim("tokenType", tokenType)
                .claim("tokenVersion", user.getTokenVersion())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractTokenType(String token) {
        return extractClaims(token).get("tokenType").toString();
    }

    public Long extractTokenVersion(String token) {
        return extractClaims(token).get("tokenVersion", Number.class).longValue();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractClaims(token);

            return claims.getExpiration() != null
                    && claims.getExpiration().after(new Date())
                    && claims.getSubject() != null
                    && claims.get("tokenType", String.class) != null
                    && claims.get("tokenVersion", Number.class) != null;

        } catch (io.jsonwebtoken.JwtException |
                 IllegalArgumentException ex) {
            return false;
        }
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
