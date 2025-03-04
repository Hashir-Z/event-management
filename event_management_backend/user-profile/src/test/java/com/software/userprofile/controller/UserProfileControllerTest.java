package com.software.userprofile.controller;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test; // For List<>
import static org.mockito.Mockito.when; // For the post method
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.software.userprofile.config.TestApplication;
import com.software.userprofile.model.UserProfile;
import com.software.userprofile.service.UserProfileService;

@WebMvcTest(UserProfileController.class)
@ContextConfiguration(classes = TestApplication.class)
public class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProfileService userProfileService;

    @Test
void testGetAllUserProfiles() throws Exception {
    // Given
    UserProfile userProfile1 = new UserProfile(
        "John Doe", "123 Main St", "Apt 4", "Houston", "TX", "77001",
        Arrays.asList("Java", "Spring"), "Available after 5PM", Arrays.asList("2025-03-10")
    );
    UserProfile userProfile2 = new UserProfile(
        "Jane Doe", "456 Oak St", "Apt 5", "Austin", "TX", "73301",
        Arrays.asList("JavaScript", "NodeJS"), "Available all day", Arrays.asList("2025-05-12")
    );
    List<UserProfile> userProfiles = Arrays.asList(userProfile1, userProfile2);
    
    // Mocking the service call
    when(userProfileService.getUserProfiles()).thenReturn(userProfiles);
    
    // When and Then
    mockMvc.perform(get("/api/user-profiles/all"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.length()").value(2))
           .andExpect(jsonPath("$[0].fullName", is("John Doe")))
           .andExpect(jsonPath("$[1].fullName", is("Jane Doe")));
}


    @Test
    public void testGetUserProfile() throws Exception {
        UserProfile userProfile = new UserProfile(
            "John Doe", "123 Main St", "Apt 4", "Houston", "TX", "77001",
            Arrays.asList("Java", "Spring"), "Available after 5PM", Arrays.asList("2025-03-10")
        );
    
        when(userProfileService.getUserProfile("John Doe")).thenReturn(userProfile);
    
        mockMvc.perform(get("/api/user-profiles/John Doe"))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$.fullName", is("John Doe")));

    }           
}
