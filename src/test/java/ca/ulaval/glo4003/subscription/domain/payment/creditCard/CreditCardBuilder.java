package ca.ulaval.glo4003.subscription.domain.payment.creditCard;

import java.time.LocalDate;

public class CreditCardBuilder {

    private final String hashedNumber;
    private final String ownerName;
    private final String hashedSecurityCode;
    private final LocalDate expirationDate;

    public CreditCardBuilder() {
        this.hashedNumber = "ABC";
        this.ownerName = "Paul";
        this.hashedSecurityCode = "12931u293jaijd9ajsd9";
        this.expirationDate = LocalDate.MAX;
    }

    public CreditCard build() {
        return new CreditCard(
                this.hashedNumber,
                this.ownerName,
                this.hashedSecurityCode,
                this.expirationDate
        );
    }
}
