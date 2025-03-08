package com.software.common.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventManagementPrincipalTest {

    @Test
    void testEventManagementPrincipalGetterAndSetter() {
        EventManagementPrincipal principal = new EventManagementPrincipal();

        principal.setId("123");
        principal.setEmail("test@example.com");
        principal.setFullName("Test User");
        principal.setAdmin(true);

        assertEquals("123", principal.getId());
        assertEquals("test@example.com", principal.getEmail());
        assertEquals("Test User", principal.getFullName());
        assertTrue(principal.isAdmin());
    }

    @Test
    void testDefaultValues() {
        EventManagementPrincipal principal = new EventManagementPrincipal();

        assertNull(principal.getId());
        assertNull(principal.getEmail());
        assertNull(principal.getFullName());
        assertFalse(principal.isAdmin());
    }
}
