package com.software.auth.service;

import com.software.auth.repository.TokenRepository;
import com.software.auth.token.Token;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import static org.mockito.Mockito.*;

public class LogoutServiceTest {

    @InjectMocks
    private LogoutService logoutService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void logout_ShouldDoNothing_WhenAuthHeaderIsMissing() {
        when(request.getHeader("Authorization")).thenReturn(null);

        logoutService.logout(request, response, authentication);

        verifyNoInteractions(tokenRepository);
    }

    @Test
    void logout_ShouldDoNothing_WhenAuthHeaderDoesNotStartWithBearer() {
        when(request.getHeader("Authorization")).thenReturn("InvalidHeader");

        logoutService.logout(request, response, authentication);

        verifyNoInteractions(tokenRepository);
    }

    @Test
    void logout_ShouldExpireAndRevokeToken_WhenValidTokenProvided() {
        String jwt = "valid-jwt-token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);

        var storedToken = mock(Token.class);
        when(tokenRepository.findByToken(jwt)).thenReturn(java.util.Optional.of(storedToken));

        logoutService.logout(request, response, authentication);

        verify(storedToken).setExpired(true);
        verify(storedToken).setRevoked(true);
        verify(tokenRepository).save(storedToken);
    }

    @Test
    void logout_ShouldDoNothing_WhenTokenNotFound() {
        String jwt = "nonexistent-jwt-token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);

        when(tokenRepository.findByToken(jwt)).thenReturn(java.util.Optional.empty());

        logoutService.logout(request, response, authentication);

        verify(tokenRepository, never()).save(any());
    }
}
