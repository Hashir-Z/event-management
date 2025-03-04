package com.software.userprofile.model;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

class UserProfileTest {

    @Test
    void testUserProfileConstructor() {
        UserProfile userProfile = new UserProfile(
            "John Doe", "123 Main St", "Apt 4", "Houston", "TX", "77001",
            Arrays.asList("Java", "Spring"), "Available after 5PM", Arrays.asList("2025-03-10")
        );
        assertNotNull(userProfile);
        assertEquals("John Doe", userProfile.getFullName());
    }
}
