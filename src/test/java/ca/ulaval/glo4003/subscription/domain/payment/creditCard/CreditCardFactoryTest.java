package ca.ulaval.glo4003.subscription.domain.payment.creditCard;

import ca.ulaval.glo4003.main.domain.hash.HashingTool;
import ca.ulaval.glo4003.subscription.domain.exceptions.ExpiredCreditCardException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreditCardFactoryTest {

    public static final String NUMBER = "allo";
    public static final String SECURITY_CODE = "yooooooooooooo";
    private static final String HASHED_NUMBER = HashingTool.hashString(NUMBER);
    private static final String OWNER_NAME = "PAUL";
    private static final String HASHED_SECURITY_CODE = HashingTool.hashString(SECURITY_CODE);
    private static final LocalDate EXPIRATION_DATE = LocalDate.MAX;

    private final CreditCard expectedCreditCard = new CreditCard(HASHED_NUMBER, OWNER_NAME,
            HASHED_SECURITY_CODE, EXPIRATION_DATE);

    private final CreditCardFactory creditCardFactory = new CreditCardFactory();

    @Test
    public void whenCreatingCreditCard_thenAllFieldsAreAssignedCorrectly() {
        CreditCard creditCard = creditCardFactory.create(NUMBER, OWNER_NAME,
                SECURITY_CODE, EXPIRATION_DATE);

        assertEquals(expectedCreditCard, creditCard);
    }

    @Test
    public void givenExpirationDateInThePast_whenCreatingCreditCard_thenShouldNotCreateIt() {
        LocalDate expirationDateInThePast = LocalDate.MIN;

        Executable createCreditCard = () -> creditCardFactory.create(NUMBER, OWNER_NAME,
                SECURITY_CODE, expirationDateInThePast);

        assertThrows(ExpiredCreditCardException.class, createCreditCard);
    }

}
