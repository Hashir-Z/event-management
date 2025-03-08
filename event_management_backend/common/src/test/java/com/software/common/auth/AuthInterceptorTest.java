package com.software.common.auth;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.software.common.entity.CommonUserEntity;
import com.software.common.service.PrincipalService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Optional;

class AuthInterceptorTest {

    @InjectMocks
    private AuthInterceptor authInterceptor;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private PrincipalService principalService;

    @Mock
    private EventManagementPrincipal eventManagementPrincipal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPreHandle_NoAuthorizationHeader() throws IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        boolean result = authInterceptor.preHandle(request, response, new Object());

        assertTrue(result);
        verify(response, never()).sendError(anyInt());
        verify(principalService, never()).getUserDetails(anyString());
    }

    @Test
    void testPreHandle_InvalidToken() throws IOException {
        when(request.getHeader("Authorization")).thenReturn("InvalidToken");
        when(principalService.getUserDetails("InvalidToken")).thenReturn(Optional.empty());

        boolean result = authInterceptor.preHandle(request, response, new Object());

        assertFalse(result);
        verify(response, times(1)).sendError(HttpStatus.FORBIDDEN.value());
        verify(principalService, times(1)).getUserDetails("InvalidToken");
    }

    @Test
    void testPreHandle_ValidToken_UserEntityPresent() throws IOException {
        String token = "ValidToken";
        CommonUserEntity userEntity = CommonUserEntity.builder()
                .fullName("Test User")
                .email("test@example.com")
                .password("password")
                .isAdmin(true)
                .build();

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(principalService.getUserDetails(token)).thenReturn(Optional.of(userEntity));

        boolean result = authInterceptor.preHandle(request, response, new Object());

        assertTrue(result);
        verify(eventManagementPrincipal, times(1)).setId(userEntity.getId());
        verify(eventManagementPrincipal, times(1)).setEmail(userEntity.getEmail());
        verify(eventManagementPrincipal, times(1)).setFullName(userEntity.getFullName());
        verify(eventManagementPrincipal, times(1)).setAdmin(userEntity.getIsAdmin());
        verify(response, never()).sendError(anyInt());
    }

    @Test
    void testPreHandle_ValidToken_UserEntityNotPresent() throws IOException {
        String token = "ValidToken";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(principalService.getUserDetails(token)).thenReturn(Optional.empty());

        boolean result = authInterceptor.preHandle(request, response, new Object());

        assertFalse(result);
        verify(response, times(1)).sendError(HttpStatus.FORBIDDEN.value());
        verify(principalService, times(1)).getUserDetails(token);
        verify(eventManagementPrincipal, never()).setId(any());
        verify(eventManagementPrincipal, never()).setEmail(any());
        verify(eventManagementPrincipal, never()).setFullName(any());
        verify(eventManagementPrincipal, never()).setAdmin(anyBoolean());
    }
}
