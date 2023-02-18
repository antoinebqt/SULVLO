package ca.ulaval.glo4003.subscription.domain.payment.creditCard;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreditCardTest {

    private static final String A_CARD_NUMBER = "a number";
    private static final String A_NAME = "a name";
    private static final String A_SECURITY_CODE = "007";
    private static final Duration A_NUMBER_OF_PAST_DAYS = Duration.ofDays(-2978);
    private static final Duration A_NUMBER_OF_FUTURE_DAYS = Duration.ofDays(588);
    private static final Clock FIXED_CLOCK =
            Clock.fixed(Instant.now().truncatedTo(ChronoUnit.SECONDS), ZoneId.of("UTC"));

    @Test
    public void givenAnInThePastExpirationDate_whenCheckingIfCardIsExpired_thenShouldBeExpired() {
        LocalDate anInThePastExpirationDate = LocalDate.now(Clock.offset(FIXED_CLOCK, A_NUMBER_OF_PAST_DAYS));
        CreditCard creditCard =
                new CreditCard(A_CARD_NUMBER, A_NAME, A_SECURITY_CODE, anInThePastExpirationDate, FIXED_CLOCK);

        boolean isExpired = creditCard.isExpired();

        assertTrue(isExpired);
    }

    @Test
    public void givenTheExpirationDateIsToday_whenCheckingIfCardIsExpired_thenShouldBeExpired() {
        LocalDate todayExpirationDate = LocalDate.now(FIXED_CLOCK);
        CreditCard creditCard =
                new CreditCard(A_CARD_NUMBER, A_NAME, A_SECURITY_CODE, todayExpirationDate, FIXED_CLOCK);

        boolean isExpired = creditCard.isExpired();

        assertTrue(isExpired);
    }

    @Test
    public void givenAnInTheFutureExpirationDate_whenCheckingIfCardIsExpired_thenShouldNotBeExpired() {
        LocalDate anInTheFutureExpirationDate = LocalDate.now(Clock.offset(FIXED_CLOCK, A_NUMBER_OF_FUTURE_DAYS));
        CreditCard creditCard =
                new CreditCard(A_CARD_NUMBER, A_NAME, A_SECURITY_CODE, anInTheFutureExpirationDate, FIXED_CLOCK);

        boolean isExpired = creditCard.isExpired();

        assertFalse(isExpired);
    }

}
