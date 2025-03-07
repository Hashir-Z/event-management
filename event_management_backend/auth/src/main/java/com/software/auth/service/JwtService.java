package com.software.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import com.software.auth.entity.User;
import com.software.auth.repository.TokenRepository;
import com.software.clients.uam.UserAccessManagementClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    String secretKey;
    @Value("${application.security.jwt.expiration}")
    long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    long refreshExpiration;

    private final UserAccessManagementClient userAccessManagementClient;
    private final TokenRepository tokenRepository;


    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim (String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return generateToken(claims, userName);
    }

    public String generateToken (Map<String, Object> claims, String userName) {
        return buildToken(claims, userName, jwtExpiration);
    }

    public String generateRefreshToken (String userName) {
        return buildToken(new HashMap<>(), userName, refreshExpiration);
    }

    public boolean isTokenValid (String token, String email) {
        final String tokenEmail = extractEmail(token);
        return tokenEmail.equals(email) && !isTokenExpired(token);
    }

    public boolean isTokenExpired (String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValidated (String token) {
        String jwt = token.substring(7);
        String email = extractEmail(jwt);
        UserDetails userDetails = userAccessManagementClient.findByEmail(email).map(User::new).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        boolean isTokenValid = tokenRepository.findByToken(jwt).map(t -> !t.isRevoked() && !t.isExpired()).orElse(false);
        return isTokenValid && isTokenValid(jwt, userDetails.getUsername());
    }

    private Date extractExpiration (String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private String buildToken(Map<String, Object> claims, String username, long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
