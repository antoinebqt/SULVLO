package ca.ulaval.glo4003.code.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnlockCodeTest {
    public static final String A_CODE_VALUE = "1234567890";
    public static final LocalDateTime A_CREATION_DATE = LocalDateTime.now();

    @Test
    public void givenJustCreatedCode_whenAskingIfIsExpired_thenShouldNotBeExpired() {
        LocalDateTime now = LocalDateTime.now();
        UnlockCode unlockCode = new UnlockCode(A_CODE_VALUE, now);

        assertFalse(unlockCode.isExpired());
    }

    @Test
    public void givenAnExpiredCreationDate_whenAskingIfIsExpired_thenShouldBeExpired() {
        LocalDateTime expiredCreationDate = LocalDateTime.now().minusSeconds(61);
        UnlockCode unlockCode = new UnlockCode(A_CODE_VALUE, expiredCreationDate);

        assertTrue(unlockCode.isExpired());
    }

    @Test
    public void whenComparingToAnotherIdenticalCode_thenShouldBeEqual() {
        UnlockCode unlockCode = new UnlockCode(A_CODE_VALUE, A_CREATION_DATE);

        boolean isEqual = unlockCode.matchesValue(A_CODE_VALUE);

        assertTrue(isEqual);
    }

    @Test
    public void whenComparingToADifferentCode_thenShouldNotBeEqual() {
        String aDifferentCodeValue = "31004141";
        UnlockCode unlockCode = new UnlockCode(A_CODE_VALUE, A_CREATION_DATE);

        boolean isEqual = unlockCode.matchesValue(aDifferentCodeValue);

        assertFalse(isEqual);
    }

}
