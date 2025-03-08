package com.software.auth.config;

import com.software.auth.entity.User;
import com.software.clients.uam.UserAccessManagementClient;
import com.software.clients.uam.UserDetailsRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

class AuthConfigTest {

    @InjectMocks
    private AuthConfig authConfig;

    @Mock
    private UserAccessManagementClient userAccessManagementClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUserDetailsService_UserExists() {
        String email = "test@example.com";
        UserDetailsRecord mockUser = new UserDetailsRecord("id", "fullName", "username", "password", "123", "123");
        when(userAccessManagementClient.findByEmail(email)).thenReturn(Optional.of(mockUser));

        UserDetailsService userDetailsService = authConfig.userDetailsService();
        assertNotNull(userDetailsService);

        User user = (User) userDetailsService.loadUserByUsername(email);
        assertNotEquals(mockUser, user);
    }

    @Test
    void testUserDetailsService_UserNotFound() {
        String email = "nonexistent@example.com";
        when(userAccessManagementClient.findByEmail(email)).thenReturn(Optional.empty());

        UserDetailsService userDetailsService = authConfig.userDetailsService();

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(email));
    }

    @Test
    void testAuthenticationManager() throws Exception {
        AuthenticationConfiguration mockAuthManager = mock(AuthenticationConfiguration.class);
        AuthenticationManager authenticationManager = authConfig.authenticationManager(mockAuthManager);

        assertNull(authenticationManager);
        assertNotEquals(mockAuthManager, authenticationManager);
    }

    @Test
    void testPasswordEncoder() {
        PasswordEncoder passwordEncoder = authConfig.passwordEncoder();
        assertNotNull(passwordEncoder);
        assertTrue(true);

        String rawPassword = "password";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }
}
