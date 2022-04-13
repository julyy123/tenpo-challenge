package com.juliansanchez.tenpochallenge.security.jwt;

import java.time.Instant;
import java.util.Date;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;

/**
 * Jwt token util class to validate and generate jwt tokens
 */
@Component
public class JwtTokenUtil {

    /**
     * THIS IS NOT A SECURE PRACTICE! For simplicity, we are storing a static key here. Ideally, in a
     * microservices environment, this key would be kept on a config-server.
     */
    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration_time_seconds}")
    private long expirationTimeSeconds;

    private SecretKey secretKey;

    @PostConstruct
    public void setSecret() {
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(expirationTimeSeconds)))
                .signWith(secretKey)
                .compact();
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    private Date getExpiration(String token) {
        return parseClaims(token).getExpiration();
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getSubject(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
