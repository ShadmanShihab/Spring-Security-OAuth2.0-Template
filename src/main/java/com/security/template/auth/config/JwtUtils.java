package com.security.template.auth.config;

import com.security.template.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.refresh.secret}")
    private String refreshSecret;
    private final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private final long JWT_REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60;

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = getUserRoles(userDetails);
        claims.put("roles", roles);
        return createToken(claims, userDetails.getUsername(), JWT_TOKEN_VALIDITY, secret);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = getUserRoles(userDetails);
        claims.put("roles", roles);
        return createToken(claims, userDetails.getUsername(), JWT_REFRESH_TOKEN_VALIDITY, refreshSecret);
    }

    private String createToken(Map<String, Object> claims, String subject, long validity, String secretKey) {
        Date tokenValidity = new Date(System.currentTimeMillis() + validity * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public List<String> getUserRoles(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public String extractUsername(String token, String secretKey) {
        return extractClaim(token, Claims::getSubject, secretKey);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, String secretKey) {
        final Claims claims = extractAllClaims(token, secretKey);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token, String secretKey) {
        return extractExpiration(token, secretKey).before(new Date());
    }

    public Date extractExpiration(String token, String secretKey) {
        return extractClaim(token, Claims::getExpiration, secretKey);
    }

    public Date extractLastIssuedAt(String token, String secretKey) {
        return extractClaim(token, Claims::getIssuedAt, secretKey);
    }

    public boolean validateToken(String token, UserDetails userDetails, String secretKey) {
        final String username = extractUsername(token, secretKey);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, secretKey));
    }

    public String getSecret() { // Added getSecret() method
        return secret;
    }

    public String getRefreshSecret() {
        return refreshSecret;
    }
}
