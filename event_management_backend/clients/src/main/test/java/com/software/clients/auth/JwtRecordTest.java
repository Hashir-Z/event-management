package com.software.clients.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtRecordTest {

    @Test
    void testJwtRecordCreation() {
        String token = "testToken123";
        JwtRecord jwtRecord = new JwtRecord(token);

        assertNotNull(jwtRecord);
        assertEquals(token, jwtRecord.token());
    }

    @Test
    void testJwtRecordEquality() {
        JwtRecord jwtRecord1 = new JwtRecord("testToken123");
        JwtRecord jwtRecord2 = new JwtRecord("testToken123");

        assertEquals(jwtRecord1, jwtRecord2);
        assertEquals(jwtRecord1.hashCode(), jwtRecord2.hashCode());
    }

    @Test
    void testJwtRecordToString() {
        JwtRecord jwtRecord = new JwtRecord("testToken123");

        String expectedString = "JwtRecord[token=testToken123]";
        assertEquals(expectedString, jwtRecord.toString());
    }
}
