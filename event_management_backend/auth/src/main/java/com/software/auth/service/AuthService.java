package com.software.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.software.auth.dto.AuthRequest;
import com.software.auth.dto.AuthResponse;
import com.software.auth.dto.UserRecord;
import com.software.auth.repository.TokenRepository;
import com.software.auth.token.Token;
import com.software.auth.token.TokenType;
import com.software.clients.uam.UserAccessManagementClient;
import com.software.clients.uam.UserAccessManagementRequest;
import com.software.clients.uam.UserDetailsRecord;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserAccessManagementClient userAccessManagementClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void register(final UserRecord userRecord) {
        UserAccessManagementRequest userAccessManagementRequest = new UserAccessManagementRequest(
                userRecord.fullName(),
                userRecord.email(),
                passwordEncoder.encode(userRecord.password()),
                userRecord.isAdmin()
        );
        userAccessManagementClient.addUser(userAccessManagementRequest);
        UserDetailsRecord userDetailsRecord = userAccessManagementClient.findByEmail(userRecord.email()).orElseThrow();
        String jwtToken = jwtService.generateToken(userRecord.email());
        saveUserToken(userDetailsRecord, jwtToken);
    }

    public AuthResponse authenticate(final AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));
        if (authenticate.isAuthenticated()) {
            UserDetailsRecord userDetailsRecord = userAccessManagementClient.findByEmail(authRequest.email()).orElseThrow();
            String accessToken = generateToken(authRequest.email());
            String refreshToken = generateRefreshToken(authRequest.email());
            saveUserToken(userDetailsRecord, accessToken);
            return AuthResponse.builder()
                    .id(userDetailsRecord.id())
                    .fullName(userDetailsRecord.fullName())
                    .email(userDetailsRecord.email())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            throw new RuntimeException("Invalid Access");
        }
    }

    private void saveUserToken(UserDetailsRecord user, String jwtToken) {
        Token token = Token.builder()
                .userId(user.id())
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public String generateToken(final String username) {
        return jwtService.generateToken(username);
    }

    public String generateRefreshToken(final String username) {
        return jwtService.generateRefreshToken(username);
    }

    public void validateToken(final String token) {
        jwtService.validateToken(token);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String email;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        email = jwtService.extractEmail(refreshToken);
        if (email != null) {
            UserDetailsRecord userDetailsRecord = userAccessManagementClient.findByEmail(email)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, userDetailsRecord.email())) {
                String accessToken = jwtService.generateToken(userDetailsRecord.email());
                saveUserToken(userDetailsRecord, accessToken);
                AuthResponse authResponse = AuthResponse.builder()
                        .id(userDetailsRecord.id())
                        .fullName(userDetailsRecord.fullName())
                        .email(userDetailsRecord.email())
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
