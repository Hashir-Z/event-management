package com.software.common.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.software.common.entity.CommonUserEntity;
import com.software.common.repository.CommonUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class PrincipalServiceTest {

    @InjectMocks
    private PrincipalService principalService;

    @Mock
    private CommonUserRepository commonUserRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserDetails_UserExists() {
        String token = "validToken";
        CommonUserEntity userEntity = CommonUserEntity.builder()
                .fullName("Test User")
                .email("test@example.com")
                .password("password")
                .isAdmin(true)
                .build();

        when(commonUserRepository.findByToken(token)).thenReturn(Optional.of(userEntity));

        Optional<CommonUserEntity> result = principalService.getUserDetails(token);

        assertTrue(result.isPresent());
        assertEquals(userEntity.getId(), result.get().getId());
        assertEquals(userEntity.getEmail(), result.get().getEmail());
        assertEquals(userEntity.getFullName(), result.get().getFullName());
        assertEquals(userEntity.getIsAdmin(), result.get().getIsAdmin());
        verify(commonUserRepository, times(1)).findByToken(token);
    }

    @Test
    void testGetUserDetails_UserDoesNotExist() {
        String token = "invalidToken";

        when(commonUserRepository.findByToken(token)).thenReturn(Optional.empty());

        Optional<CommonUserEntity> result = principalService.getUserDetails(token);

        assertFalse(result.isPresent());
        verify(commonUserRepository, times(1)).findByToken(token);
    }
}
