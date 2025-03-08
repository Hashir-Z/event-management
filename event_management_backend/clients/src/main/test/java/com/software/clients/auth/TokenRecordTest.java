package com.software.clients.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenRecordTest {

    @Test
    void testTokenRecordCreation() {
        TokenRecord tokenRecord = new TokenRecord("1", "101", "testToken", "BEARER", false, false);

        assertNotNull(tokenRecord);
        assertEquals("1", tokenRecord.id());
        assertEquals("101", tokenRecord.userId());
        assertEquals("testToken", tokenRecord.token());
        assertEquals("BEARER", tokenRecord.tokenType());
        assertFalse(tokenRecord.revoked());
        assertFalse(tokenRecord.expired());
    }

    @Test
    void testTokenRecordEquality() {
        TokenRecord tokenRecord1 = new TokenRecord("1", "101", "testToken", "BEARER", false, false);
        TokenRecord tokenRecord2 = new TokenRecord("1", "101", "testToken", "BEARER", false, false);

        assertEquals(tokenRecord1, tokenRecord2);
        assertEquals(tokenRecord1.hashCode(), tokenRecord2.hashCode());
    }

    @Test
    void testTokenRecordToString() {
        TokenRecord tokenRecord = new TokenRecord("1", "101", "testToken", "BEARER", false, false);

        String expectedString = "TokenRecord[id=1, userId=101, token=testToken, tokenType=BEARER, revoked=false, expired=false]";
        assertEquals(expectedString, tokenRecord.toString());
    }
}
