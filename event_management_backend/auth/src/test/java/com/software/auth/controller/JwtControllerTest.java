package com.software.auth.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.clients.auth.JwtRecord;
import com.software.auth.service.JwtService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Controller - Jwt")
@SpringBootTest(classes = JwtController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureWebMvc
public class JwtControllerTest {

    private static final String BASE_URL = "/jwt";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        when(jwtService.isTokenValidated("validToken")).thenReturn(true);
        when(jwtService.isTokenValidated("invalidToken")).thenReturn(false);
    }

    @Test
    @DisplayName("Validate token - valid token")
    void testValidateTokenWithValidToken() throws Exception {
        JwtRecord jwtRecord = new JwtRecord("validToken");

        mockMvc.perform(post(BASE_URL+ "/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jwtRecord)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("Validate token- invalid token")
    void testValidateTokenWithInvalidToken() throws Exception {
        JwtRecord jwtRecord = new JwtRecord("invalidToken");

        mockMvc.perform(post(BASE_URL+"/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jwtRecord)))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
    protected byte[] asJsonString (final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsBytes(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

