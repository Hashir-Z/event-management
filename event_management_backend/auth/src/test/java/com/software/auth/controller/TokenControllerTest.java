package com.software.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.auth.service.TokenService;
import com.software.clients.auth.TokenRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Controller - Token")
@SpringBootTest(classes = TokenController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureWebMvc
public class TokenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        TokenRecord tokenRecord=new TokenRecord("12345", "user123", "validToken", "ACCESS", false, false);
        when(tokenService.findByToken("invalidToken")).thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("Get Token by Valid Token")
    void testGetTokenByValidToken() throws Exception {
        mockMvc.perform(get("/token")
                        .param("token", "validToken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Get Token by Invalid Token")
    void testGetTokenByInvalidToken() throws Exception {
        mockMvc.perform(get("/token")
                        .param("token", "invalidToken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("null"));
    }


}
