package com.software.auth.controller;

import com.software.auth.service.JwtService;
import com.software.clients.auth.JwtRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

class JwtControllerTest {

    @InjectMocks
    private JwtController jwtController;

    @Mock
    private JwtService jwtService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(jwtController).build();
    }

    @Test
    void testValidateToken_ValidToken() throws Exception {
        JwtRecord jwtRecord = new JwtRecord("validToken");
        when(jwtService.isTokenValidated(jwtRecord.token())).thenReturn(true);

        mockMvc.perform(post("/jwt/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\":\"validToken\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(jwtService, times(1)).isTokenValidated("validToken");
    }

    @Test
    void testValidateToken_InvalidToken() throws Exception {
        JwtRecord jwtRecord = new JwtRecord("invalidToken");
        when(jwtService.isTokenValidated(jwtRecord.token())).thenReturn(false);

        mockMvc.perform(post("/jwt/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\":\"invalidToken\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

        verify(jwtService, times(1)).isTokenValidated("invalidToken");
    }
}
