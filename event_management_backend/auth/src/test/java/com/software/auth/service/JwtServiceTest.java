package com.software.auth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.software.auth.repository.TokenRepository;
import com.software.auth.token.Token;
import com.software.clients.uam.UserAccessManagementClient;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserAccessManagementClient userAccessManagementClient;

    @Mock
    private TokenRepository tokenRepository;

    private final String secretKey = "c24bd868722684c9b9987b46aec79573b5ad1d199b7e606b7d4c5af82b3b99d7";
    private final long jwtExpiration = 1000 * 60 * 60;
    private final long refreshExpiration = 1000 * 60 * 60 * 24;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtService.secretKey = secretKey;
        jwtService.jwtExpiration = jwtExpiration;
        jwtService.refreshExpiration = refreshExpiration;
    }

    @Test
    void validateToken_ShouldNotThrowException_WhenTokenIsValid() {
        String token = generateValidToken("test@example.com");
        assertDoesNotThrow(() -> jwtService.validateToken(token));
    }

    @Test
    void extractEmail_ShouldReturnEmail_FromToken() {
        String email = "test@example.com";
        String token = generateValidToken(email);

        String extractedEmail = jwtService.extractEmail(token);

        assertEquals(email, extractedEmail);
    }

    @Test
    void isTokenValid_ShouldReturnTrue_WhenTokenIsValid() {
        String email = "test@example.com";
        String token = generateValidToken(email);
        Token mockToken = mockToken(false, false);
        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(mockToken));
        boolean isValid = jwtService.isTokenValid(token, email);

        assertTrue(isValid);
    }

    @Test
    void isTokenValid_ShouldReturnFalse_WhenTokenEmailDoesNotMatch() {
        String token = generateValidToken("other@example.com");

        boolean isValid = jwtService.isTokenValid(token, "test@example.com");

        assertFalse(isValid);
    }

    @Test
    void isTokenValid_ShouldReturnFalse_WhenTokenIsExpired() {
        String email = "test@example.com";
        String token = generateExpiredToken(email);

        boolean isValid;
        try {
            isValid = jwtService.isTokenValid(token, email);
        } catch (ExpiredJwtException e) {
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test
    void isTokenExpired_ShouldReturnTrue_WhenTokenIsExpired() {
        String token = generateExpiredToken("test@example.com");

        boolean isExpired;
        try {
            isExpired = jwtService.isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            isExpired = true;
        }

        assertTrue(isExpired);
    }

    @Test
    void generateToken_ShouldReturnValidJwtToken() {
        String username = "test@example.com";

        String token = jwtService.generateToken(username);

        assertNotNull(token);
        assertDoesNotThrow(() -> jwtService.validateToken(token));
    }

    @Test
    void generateRefreshToken_ShouldReturnValidJwtToken() {
        String username = "test@example.com";

        String token = jwtService.generateRefreshToken(username);

        assertNotNull(token);
        assertDoesNotThrow(() -> jwtService.validateToken(token));
    }

    @Test
    void isTokenValidated_ShouldThrowException_WhenUserNotFound() {
        String jwt = generateValidToken("nonexistent@example.com");

        when(userAccessManagementClient.findByEmail("nonexistent@example.com")).thenThrow(UsernameNotFoundException.class);

        assertThrows(UsernameNotFoundException.class, () -> jwtService.isTokenValidated("Bearer " + jwt));
    }

    // Helper method to generate a valid token
    private String generateValidToken(String email) {
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Helper method to generate an expired token
    private String generateExpiredToken(String email) {
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis() - jwtExpiration))
                .setExpiration(new Date(System.currentTimeMillis()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Mock a Token object
    private Token mockToken(boolean isExpired, boolean isRevoked) {
        Token token = mock(Token.class);
        when(token.isExpired()).thenReturn(isExpired);
        when(token.isRevoked()).thenReturn(isRevoked);
        return token;
    }
}
