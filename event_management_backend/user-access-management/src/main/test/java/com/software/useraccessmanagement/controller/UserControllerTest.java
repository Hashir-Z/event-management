package com.software.useraccessmanagement.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.software.clients.uam.UserAccessManagementRequest;
import com.software.clients.uam.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.software.useraccessmanagement.service.UserService;

import java.util.Optional;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        UserAccessManagementRequest request = new UserAccessManagementRequest(
                "John Doe",
                "johndoe@example.com",
                "securePassword123",
                true
        );

        userController.addUser(request);

        verify(userService, times(1)).addUser(request);
    }

    @Test
    void testGetUser() {
        String token = "sample-token";
        Object expectedResponse = new Object();
        when(userService.getUser(token)).thenReturn(expectedResponse);

        Object response = userController.getUser(token);

        assertEquals(expectedResponse, response);
        verify(userService, times(1)).getUser(token);
    }

    @Test
    void testFindByEmail() {
        String email = "test@example.com";
        UserAccessManagementRequest request = new UserAccessManagementRequest(
                "John Doe",
                "johndoe@example.com",
                "securePassword123",
                true
        );

        Optional<UserResponse> expectedResponse = Optional.of(new UserResponse(
                "id",
                "John Doe",
                "johndoe@example.com",
                "securePassword123",
                true
        ));
        when(userService.findByEmail(email)).thenReturn(expectedResponse);

        Optional<UserResponse> response = userController.findByEmail(email);

        assertEquals(expectedResponse, response);
        verify(userService, times(1)).findByEmail(email);
    }
}
