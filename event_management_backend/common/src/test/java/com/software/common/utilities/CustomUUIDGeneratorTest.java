package com.software.common.utilities;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class CustomUUIDGeneratorTest {

    @Test
    public void testGenerateUUID() {
        CustomUUIDGenerator generator = new CustomUUIDGenerator();
        SharedSessionContractImplementor mockSession = mock(SharedSessionContractImplementor.class);
        Object mockObject = new Object();

        Serializable generatedUUID = generator.generate(mockSession, mockObject);

        assertNotNull(generatedUUID, "Generated UUID should not be null");
        assertDoesNotThrow(() -> UUID.fromString(generatedUUID.toString()), "Generated UUID should be a valid UUID");
    }
}
