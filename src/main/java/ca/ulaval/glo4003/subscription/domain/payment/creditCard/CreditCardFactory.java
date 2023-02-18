package ca.ulaval.glo4003.subscription.domain.payment.creditCard;

import ca.ulaval.glo4003.main.domain.hash.HashingTool;
import ca.ulaval.glo4003.subscription.domain.exceptions.ExpiredCreditCardException;

import java.time.LocalDate;

public class CreditCardFactory {

    public CreditCard create(String number, String ownerName, String securityCode, LocalDate expiration) {
        if (LocalDate.now().isAfter(expiration)) {
            throw new ExpiredCreditCardException();
        }
        String hashedNumber = HashingTool.hashString(number);
        String hashedSecurityCode = HashingTool.hashString(securityCode);

        return new CreditCard(hashedNumber, ownerName, hashedSecurityCode, expiration);
    }

}
