package ca.ulaval.glo4003.main.domain.hash;


import org.junit.jupiter.api.Test;

import static ca.ulaval.glo4003.main.domain.hash.HashingTool.hashString;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HashingToolTest {

    @Test
    public void givenAPassword_whenHashing_thenShouldReturnSHA256HashedValue() {
        String aPassword = "password";
        String expectedHashValue = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";

        String hashedValue = hashString(aPassword);

        assertEquals(expectedHashValue, hashedValue);
    }

    @Test
    public void givenAPassword_whenHashing_thenShouldReturn64DigitHashedValue() {
        String aPassword = "password";
        int expectedLength = 64;

        String hashedValue = hashString(aPassword);

        assertEquals(expectedLength, hashedValue.length());
    }

}