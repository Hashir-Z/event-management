package com.software.auth.utilities;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class CustomUUIDGeneratorTest {

    private CustomUUIDGenerator customUUIDGenerator;

    @Mock
    private SharedSessionContractImplementor session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customUUIDGenerator = new CustomUUIDGenerator();
        session = mock(SharedSessionContractImplementor.class);
    }

    @Test
    void testGenerate() {
        Object object = new Object();
        Serializable uuid = customUUIDGenerator.generate(session, object);
        assertNotNull(uuid);
        assert uuid.toString().matches("^[a-f0-9\\-]{36}$");
    }
}
