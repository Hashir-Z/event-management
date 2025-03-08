package com.software.clients.uam;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserAccessManagementRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUserAccessManagementRequest() {
        UserAccessManagementRequest request = new UserAccessManagementRequest(
                "John Doe",
                "john.doe@example.com",
                "securePassword123",
                true
        );

        Set<ConstraintViolation<UserAccessManagementRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidUserAccessManagementRequest_BlankFields() {
        UserAccessManagementRequest request = new UserAccessManagementRequest(
                "",
                "",
                "",
                null
        );

        Set<ConstraintViolation<UserAccessManagementRequest>> violations = validator.validate(request);

        assertEquals(4, violations.size());
    }

    @Test
    void testInvalidUserAccessManagementRequest_NullIsAdmin() {
        UserAccessManagementRequest request = new UserAccessManagementRequest(
                "John Doe",
                "john.doe@example.com",
                "securePassword123",
                null
        );

        Set<ConstraintViolation<UserAccessManagementRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<UserAccessManagementRequest> violation = violations.iterator().next();
        assertEquals("isAdmin is required", violation.getMessage());
    }
}
