package com.software.clients.uam;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseTest {

    @Test
    void testUserResponseCreation() {
        UserResponse userResponse = new UserResponse("1", "John Doe", "john.doe@example.com", "password123", true);

        assertNotNull(userResponse);
        assertEquals("1", userResponse.id());
        assertEquals("John Doe", userResponse.fullName());
        assertEquals("john.doe@example.com", userResponse.email());
        assertEquals("password123", userResponse.password());
        assertTrue(userResponse.isAdmin());
    }

    @Test
    void testUserResponseEquality() {
        UserResponse userResponse1 = new UserResponse("1", "John Doe", "john.doe@example.com", "password123", true);
        UserResponse userResponse2 = new UserResponse("1", "John Doe", "john.doe@example.com", "password123", true);

        assertEquals(userResponse1, userResponse2);
        assertEquals(userResponse1.hashCode(), userResponse2.hashCode());
    }

    @Test
    void testUserResponseToString() {
        UserResponse userResponse = new UserResponse("1", "John Doe", "john.doe@example.com", "password123", true);

        String expectedString = "UserResponse[id=1, fullName=John Doe, email=john.doe@example.com, password=password123, isAdmin=true]";
        assertEquals(expectedString, userResponse.toString());
    }
}
