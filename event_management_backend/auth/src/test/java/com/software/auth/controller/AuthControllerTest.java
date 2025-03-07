package com.software.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.software.auth.dto.AuthRequest;
import com.software.auth.dto.AuthResponse;
import com.software.auth.dto.UserRecord;
import com.software.auth.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Controller - Auth")
@SpringBootTest(classes = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureWebMvc
class AuthControllerTest {

    private static final String BASE_URL = "/auth";

    @MockBean
    AuthService authService;

    @Autowired
    MockMvc mockMvc;

    private UserRecord userRecord;
    Random random = new Random();

    @BeforeEach
    void beforeEachTest () {
        userRecord = new UserRecord(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                random.nextBoolean()
        );
    }

    @Test
    @DisplayName("User Creation - Success")
    void addUserSuccess() throws Exception {
        ArgumentCaptor<UserRecord> userRecordArgumentCaptor = ArgumentCaptor.forClass(UserRecord.class);
        doNothing().when(authService).register(userRecordArgumentCaptor.capture());
        mockMvc.perform(post(BASE_URL + "/register")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(userRecord))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
        ).andExpect(status().isOk());
        UserRecord capturedUserRecord = userRecordArgumentCaptor.getValue();
        assertEquals(userRecord, capturedUserRecord);
    }

    @Test
    @DisplayName("Token Validation - Success")
    void validateToken() throws Exception {
        String randomToken = UUID.randomUUID().toString();
        doNothing().when(authService).validateToken(anyString());
        mockMvc.perform(get(BASE_URL + "/validate")
                        .param("token", randomToken)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Token is valid"));
        verify(authService).validateToken(randomToken);
    }

    @Test
    @DisplayName("Token Generation - Success")
    void getToken() throws Exception {
        AuthRequest authRequest = new AuthRequest("username", "password");
        when(authService.authenticate(authRequest)).thenReturn(new AuthResponse(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                "username",
                UUID.randomUUID().toString(),
                random.nextBoolean(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        ));
        mockMvc.perform(post(BASE_URL + "/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(authRequest))
                        .characterEncoding(StandardCharsets.UTF_8.name())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
        verify(authService).authenticate(authRequest);
    }

    @Test
    @DisplayName("Token Refresh - Success")
    void refreshToken() throws Exception {
        doNothing().when(authService).refreshToken(any(HttpServletRequest.class), any(HttpServletResponse.class));

        mockMvc.perform(post(BASE_URL + "/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.name())
                )
                .andExpect(status().isOk());
        verify(authService).refreshToken(any(HttpServletRequest.class), any(HttpServletResponse.class));
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