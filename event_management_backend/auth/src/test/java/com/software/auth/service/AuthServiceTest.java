package com.software.auth.service;

import com.software.auth.dto.AuthRequest;
import com.software.auth.dto.AuthResponse;
import com.software.auth.dto.UserRecord;
import com.software.auth.repository.TokenRepository;
import com.software.clients.uam.UserAccessManagementClient;
import com.software.clients.uam.UserAccessManagementRequest;
import com.software.clients.uam.UserDetailsRecord;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootApplication
@SpringBootTest(classes = AuthService.class)
class AuthServiceTest
{

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
    Random random = new Random();

    @BeforeEach
    void setup()
    {
        authRequest = new AuthRequest("username", "password");
        userDetails = new UserDetailsRecord("id", "fullName", "username", "password", "123", "123");
        userRecord = new UserRecord("fullName", "email.com", "password", random.nextBoolean());
    }

    @Test @DisplayName("Register User Details - Success")
    void register()
    {
        UserDetailsRecord userDetails1 = new UserDetailsRecord("id", userRecord.fullName(), userRecord.email(),
                userRecord.password(), "", "");
        Optional<UserDetailsRecord> user = Optional.of(userDetails1);
        doNothing().when(userAccessManagementClient).addUser(any(UserAccessManagementRequest.class));
        when(userAccessManagementClient.findByEmail(userRecord.email())).thenReturn(user);
        when(jwtService.generateToken(userRecord.email())).thenReturn("");
        when(passwordEncoder.encode(userRecord.password())).thenReturn(userRecord.password());
        this.authService.register(userRecord);
        verify(userAccessManagementClient, times(1)).findByEmail(userRecord.email());
        verify(jwtService, times(1)).generateToken(userRecord.email());
    }

    @Test @DisplayName("AuthRequest - Success")
    void authenticate_Success()
    {
        Object principal = "username";
        Object credentials = "password";
        Optional<UserDetailsRecord> user = Optional.of(userDetails);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                principal, credentials);
        boolean isAuthenticated = true;
        when(authenticationManager.authenticate(usernamePasswordAuthenticationToken)).thenReturn(authenticate);
        when(authenticate.isAuthenticated()).thenReturn(isAuthenticated);
        when(userAccessManagementClient.findByEmail(authRequest.email())).thenReturn(user);
        when(jwtService.generateToken(authRequest.email())).thenReturn("generatedToken");
        when(jwtService.generateRefreshToken(authRequest.email())).thenReturn("refreshToken");
        AuthResponse actualAuthResponse = this.authService.authenticate(authRequest);
        verify(userAccessManagementClient, times(1)).findByEmail(authRequest.email());
        verify(authenticate, times(1)).isAuthenticated();
        verify(authenticationManager, times(1)).authenticate(usernamePasswordAuthenticationToken);
        Assertions.assertEquals(authRequest.email(), actualAuthResponse.getEmail());
    }

    @Test @DisplayName("AuthRequest - Failure")
    void authenticate_Failure()
    {
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Invalid Access"));
        BadCredentialsException exception = assertThrows(BadCredentialsException.class,
                () -> authService.authenticate(authRequest));
        assertEquals("Invalid Access", exception.getMessage());
    }

    @Test
    void register_ShouldSaveUserToken()
    {
        UserRecord userRecord = new UserRecord("John Doe", "johndoe@example.com", "password", true);
        UserAccessManagementRequest userAccessRequest = new UserAccessManagementRequest(userRecord.fullName(),
                userRecord.email(), "encodedPassword", userRecord.isAdmin());
        UserDetailsRecord userDetailsRecord = new UserDetailsRecord("id", "John Doe", "johndoe@example.com", "password",
                "123", "123");

        when(passwordEncoder.encode(userRecord.password())).thenReturn("encodedPassword");
        when(userAccessManagementClient.findByEmail(userRecord.email())).thenReturn(Optional.of(userDetailsRecord));
        when(jwtService.generateToken(userRecord.email())).thenReturn("jwtToken");

        authService.register(userRecord);

        verify(userAccessManagementClient).addUser(userAccessRequest);
        verify(tokenRepository).save(any());
    }

    @Test
    void authenticate_ShouldReturnAuthResponse_OnValidCredentials()
    {
        AuthRequest authRequest = new AuthRequest("johndoe@example.com", "password");
        Authentication authentication = mock(Authentication.class);
        UserDetailsRecord userDetailsRecord = new UserDetailsRecord("id", "John Doe", "johndoe@example.com", "password",
                "123", "123");

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(userAccessManagementClient.findByEmail(authRequest.email())).thenReturn(Optional.of(userDetailsRecord));
        when(jwtService.generateToken(authRequest.email())).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(authRequest.email())).thenReturn("refreshToken");

        AuthResponse response = authService.authenticate(authRequest);

        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
        verify(tokenRepository).save(any());
    }

    @Test
    void authenticate_ShouldThrowException_OnInvalidCredentials()
    {
        AuthRequest authRequest = new AuthRequest("johndoe@example.com", "wrongpassword");
        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Invalid Access"));

        assertThrows(RuntimeException.class, () -> authService.authenticate(authRequest));
    }

    @Test
    void generateToken_ShouldReturnJwtToken()
    {
        when(jwtService.generateToken("username")).thenReturn("jwtToken");

        String token = authService.generateToken("username");

        assertEquals("jwtToken", token);
    }

    @Test
    void generateRefreshToken_ShouldReturnRefreshToken()
    {
        when(jwtService.generateRefreshToken("username")).thenReturn("refreshToken");

        String token = authService.generateRefreshToken("username");

        assertEquals("refreshToken", token);
    }

    @Test
    void validateToken_ShouldInvokeJwtService()
    {
        authService.validateToken("jwtToken");

        verify(jwtService).validateToken("jwtToken");
    }

    @Test
    void refreshToken_ShouldUpdateAccessToken2() throws Exception
    {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        UserDetailsRecord userDetailsRecord = new UserDetailsRecord("id", "John Doe", "johndoe@example.com", "password",
                "123", "123");

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer refreshToken");

        when(jwtService.extractEmail("refreshToken")).thenReturn("johndoe@example.com");
        when(userAccessManagementClient.findByEmail("johndoe@example.com")).thenReturn(Optional.of(userDetailsRecord));
        when(jwtService.isTokenValid("refreshToken", "johndoe@example.com")).thenReturn(true);
        when(jwtService.generateToken("johndoe@example.com")).thenReturn("newAccessToken");

        OutputStream outputStream = new ByteArrayOutputStream();
        when(response.getOutputStream()).thenReturn(new ServletOutputStream()
        {
            @Override
            public void write(int b)
            {
                try
                {
                    outputStream.write(b);
                } catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public boolean isReady()
            {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener)
            {
            }
        });

        authService.refreshToken(request, response);
        verify(tokenRepository).save(any());
        verify(response).getOutputStream();
    }
}