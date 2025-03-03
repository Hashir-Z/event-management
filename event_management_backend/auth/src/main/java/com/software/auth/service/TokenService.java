package com.software.auth.service;

import lombok.RequiredArgsConstructor;
import com.software.auth.repository.TokenRepository;
import com.software.auth.token.Token;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public Optional<Token> findByToken (final String token) {
        return tokenRepository.findByToken(token);
    }
}
