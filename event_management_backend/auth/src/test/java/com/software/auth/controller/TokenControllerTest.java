package com.software.auth.controller;

import com.software.auth.service.TokenService;
import com.software.clients.auth.TokenRecord;
import com.software.auth.token.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TokenControllerTest {

    @InjectMocks
    private TokenController tokenController;

    @Mock
    private TokenService tokenService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tokenController).build();
    }

    @Test
    void testFindByToken_TokenExists() throws Exception {
        String token = "validToken";
        Token mockToken = new Token("id", "validToken", TokenType.BEARER, false, false);
        when(tokenService.findByToken(token)).thenReturn(Optional.of(mockToken));

        mockMvc.perform(get("/token")
                        .param("token", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"userId\":\"id\",\"token\":\"validToken\",\"tokenType\":\"BEARER\",\"revoked\":false,\"expired\":false}"));

        verify(tokenService, times(1)).findByToken(token);
    }
}
