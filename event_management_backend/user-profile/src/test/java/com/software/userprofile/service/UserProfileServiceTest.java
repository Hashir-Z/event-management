//package com.software.userprofile.service;
//
//import java.util.Arrays;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import com.software.userprofile.model.UserProfile;
//
//class UserProfileServiceTest {
//
//    private UserProfileService userProfileService;
//
//    @BeforeEach
//    void setUp() {
//        userProfileService = new UserProfileService();
////        userProfileService.clearUserProfiles(); // Ensure each test starts fresh
//    }
//
//    @Test
//    void testCreateUserProfile() {
////        UserProfile userProfile = new UserProfile(
////            "John Doe", "123 Main St", "Apt 4", "Houston", "TX", "77001",
////            Arrays.asList("Java", "Spring"), "Available after 5PM", Arrays.asList("2025-03-10")
////        );
////
////        userProfileService.addUserProfile(userProfile);
//        assertEquals(1, userProfileService.getUserProfiles().size());
//    }
//
//    @Test
//void testUserProfileConstructor() {
//    // Given
////    UserProfile userProfile = new UserProfile(
////        "John Doe", "123 Main St", "Apt 4", "Houston", "TX", "77001",
////        Arrays.asList("Java", "Spring"), "Available after 5PM", Arrays.asList("2025-03-10")
////    );
//
//    // Then
//    assertNotNull(userProfile); // Ensure the object is created
//    assertEquals("John Doe", userProfile.getFullName());
//    assertEquals("123 Main St", userProfile.getAddress1());
//    assertEquals("Apt 4", userProfile.getAddress2());
//    assertEquals("Houston", userProfile.getCity());
//    assertEquals("TX", userProfile.getState());
//    assertEquals("77001", userProfile.getZip());
//    assertTrue(userProfile.getSkills().contains("Java"));
//    assertEquals("Available after 5PM", userProfile.getPreferences());
//    assertTrue(userProfile.getAvailability().contains("2025-03-10"));
//}
//
//@Test
//void testGettersAndSetters() {
//    // Given
//    UserProfile userProfile = new UserProfile(
//        "TEST", "TEST", "TEST", "TEST", "TEST", "TEST",
//        Arrays.asList("TEST", "TEST"), "TEST", Arrays.asList("TEST")
//    );
//
//    // Setting values
//    userProfile.setFullName("Jane Doe");
//    userProfile.setAddress1("456 Oak St");
//    userProfile.setAddress2("Apt 5");
//    userProfile.setCity("Austin");
//    userProfile.setState("TX");
//    userProfile.setZip("73301");
//    userProfile.setSkills(Arrays.asList("JavaScript", "NodeJS"));
//    userProfile.setPreferences("Available all day");
//    userProfile.setAvailability(Arrays.asList("2025-05-12"));
//
//    // Then check that the getters return the expected values
//    assertEquals("Jane Doe", userProfile.getFullName());
//    assertEquals("456 Oak St", userProfile.getAddress1());
//    assertEquals("Apt 5", userProfile.getAddress2());
//    assertEquals("Austin", userProfile.getCity());
//    assertEquals("TX", userProfile.getState());
//    assertEquals("73301", userProfile.getZip());
//    assertTrue(userProfile.getSkills().contains("JavaScript"));
//    assertEquals("Available all day", userProfile.getPreferences());
//    assertTrue(userProfile.getAvailability().contains("2025-05-12"));
//}
//
//
//    @Test
//    void testGetAllUserProfiles() {
//        UserProfile userProfile1 = new UserProfile(
//            "John Doe", "123 Main St", "Apt 4", "Houston", "TX", "77001",
//            Arrays.asList("Java", "Spring"), "Available after 5PM", Arrays.asList("2025-03-10")
//        );
//        userProfileService.addUserProfile(userProfile1);
//
//        UserProfile userProfile2 = new UserProfile(
//            "Jane Doe", "456 Oak St", null, "Dallas", "TX", "75001",
//            Arrays.asList("JavaScript", "React"), "Available mornings", Arrays.asList("2025-03-15")
//        );
//        userProfileService.addUserProfile(userProfile2);
//
//        assertEquals(2, userProfileService.getUserProfiles().size());
//    }
//}
