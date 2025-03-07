package com.software.auth.service;


import com.software.auth.dto.AuthRequest;
import com.software.auth.dto.AuthResponse;
import com.software.auth.dto.UserRecord;
import com.software.auth.repository.TokenRepository;
import com.software.clients.uam.UserAccessManagementClient;
import com.software.clients.uam.UserAccessManagementRequest;
import com.software.clients.uam.UserDetailsRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = AuthService.class)
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @MockBean
    private UserAccessManagementClient userAccessManagementClient;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private Authentication authenticate;

    private static UserRecord userRecord;

    private static UserDetailsRecord userDetails;

    private static AuthRequest authRequest;

    @BeforeEach
    void setup(){
        authRequest = new AuthRequest("username", "password");
        userDetails = new UserDetailsRecord("id", "Firstname", "Lastname", "username", "Infodat", true,"password", "123", "456",true);
        userRecord = new UserRecord("Firstname", "Lastname", "email.com", "username", "Infodat", "password");
    }

    @Test
    @DisplayName("Register User Details - Success")
    void register() {
        UserDetailsRecord userDetails1 = new UserDetailsRecord(userRecord.firstName(), userRecord.lastName(), userRecord.username(), userRecord.organizationId(), userRecord.email(),true,userRecord.password(), "", "",true);
        Optional<UserDetailsRecord> user = Optional.of(userDetails1);
        doNothing().when(userAccessManagementClient).addUser(any(UserAccessManagementRequest.class));
        when(userAccessManagementClient.findByUsername(userRecord.username())).thenReturn(user);
        when(jwtService.generateToken(userRecord.username())).thenReturn("");
        when(passwordEncoder.encode(userRecord.password())).thenReturn(userRecord.password());
        this.authService.register(userRecord);
        verify(userAccessManagementClient, times(1)).findByUsername(userRecord.username());
        verify(jwtService, times(1)).generateToken(userRecord.username());
    }

    @Test
    @DisplayName("AuthRequest - Success")
    void authenticate_Success() {
        Object principal = "username";
        Object credentials = "password";
        Optional<UserDetailsRecord> user = Optional.of(userDetails);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(principal, credentials);
        boolean isAuthenticated = true;
        when(authenticationManager.authenticate(usernamePasswordAuthenticationToken)).thenReturn(authenticate);
        when(authenticate.isAuthenticated()).thenReturn(isAuthenticated);
        when(userAccessManagementClient.findByUsername(authRequest.username())).thenReturn(user);
        when(jwtService.generateToken(authRequest.username())).thenReturn("generatedToken");
        when(jwtService.generateRefreshToken(authRequest.username())).thenReturn("refreshToken");
        AuthResponse actualAuthResponse = this.authService.authenticate(authRequest);
        verify(userAccessManagementClient, times(1)).findByUsername(authRequest.username());
        verify(authenticate, times(1)).isAuthenticated();
        verify(authenticationManager, times(1)).authenticate(usernamePasswordAuthenticationToken);
        Assertions.assertEquals(authRequest.username(), actualAuthResponse.getUsername());
    }

    @Test
    @DisplayName("AuthRequest - Failure")
    void authenticate_Failure() {
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid Access"));
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> authService.authenticate(authRequest));
        assertEquals("Invalid Access", exception.getMessage());
    }
}
