package com.software.useraccessmanagement.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.software.clients.uam.UserAccessManagementRequest;
import com.software.clients.uam.UserResponse;
import com.software.useraccessmanagement.entity.UserEntity;
import com.software.useraccessmanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UserRepository userRepository;

    private static final String OKTA_USER_ROUTE = "http://example.com/okta-user-route";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(restTemplate, userRepository);
    }

    @Test
    void testAddUser() {
        UserAccessManagementRequest request = new UserAccessManagementRequest(
                "John Doe", "johndoe@example.com", "securePassword123", true);

        UserEntity userEntity = UserEntity.builder()
                .fullName(request.fullName())
                .email(request.email())
                .password(request.password())
                .isAdmin(request.isAdmin())
                .build();

        userService.addUser(request);

        verify(userRepository, times(1)).saveAndFlush(any(UserEntity.class));
    }

    @Test
    void testFindByEmail_UserExists() {
        String email = "test@example.com";
        UserEntity userEntity = UserEntity.builder()
                .fullName("John Doe")
                .email(email)
                .password("securePassword123")
                .isAdmin(true)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        Optional<UserResponse> response = userService.findByEmail(email);

        assertTrue(response.isPresent());
        UserResponse userResponse = response.get();
        assertEquals(userEntity.getId(), userResponse.id());
        assertEquals(userEntity.getFullName(), userResponse.fullName());
        assertEquals(userEntity.getEmail(), userResponse.email());
        assertEquals(userEntity.getPassword(), userResponse.password());
        assertEquals(userEntity.getIsAdmin(), userResponse.isAdmin());

        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testFindByEmail_UserDoesNotExist() {
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<UserResponse> response;
        try
        {
            response = userService.findByEmail(email);
        }
        catch (AssertionError e){
            response = null;
        }

        assertNull(response);
        verify(userRepository, times(1)).findByEmail(email);
    }
}
