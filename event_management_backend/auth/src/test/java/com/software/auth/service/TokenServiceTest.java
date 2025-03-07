package com.software.auth.service;

import com.software.auth.repository.TokenRepository;
import com.software.auth.token.Token;
import com.software.auth.token.TokenType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Token Service Tests")
@SpringBootTest(classes = TokenService.class)
public class TokenServiceTest {

    @MockBean
    private TokenRepository tokenRepository;

    @Autowired
    private TokenService tokenService;

    private Token expectedToken = new Token();

    private final static String VALID_TOKEN = "validToken";

    private final static String INVALID_TOKEN = "invalidToken";

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        tokenService = new TokenService(tokenRepository);
        expectedToken = Token.builder()
                .userId("user123")
                .token(VALID_TOKEN)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
    }

    @Test
    @DisplayName("Success - Find Token by Valid Token")
    void testFindTokenByValidToken() {
        when(tokenRepository.findByToken(VALID_TOKEN)).thenReturn(Optional.of(expectedToken));
        Optional<Token> resultToken = tokenService.findByToken(VALID_TOKEN);
        verify(tokenRepository, times(1)).findByToken(VALID_TOKEN);
        assertEquals(Optional.of(expectedToken), resultToken);
    }

    @Test
    @DisplayName("Failure - Find Token by Invalid Token")
    void testFindTokenByInvalidToken() {
        when(tokenRepository.findByToken(INVALID_TOKEN)).thenReturn(Optional.empty());
        Optional<Token> resultToken = tokenService.findByToken(INVALID_TOKEN);
        verify(tokenRepository, times(1)).findByToken(INVALID_TOKEN);
        assertEquals(Optional.empty(), resultToken);
    }
}
