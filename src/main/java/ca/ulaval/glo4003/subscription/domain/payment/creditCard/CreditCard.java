package ca.ulaval.glo4003.subscription.domain.payment.creditCard;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;


public class CreditCard {

    private final String number;
    private final String ownerName;
    private final String securityCode;
    private final LocalDate expirationDate;
    private final Clock clock;

    public CreditCard(String hashedNumber, String ownerName, String hashedSecurityCode, LocalDate expirationDate) {
        this.number = hashedNumber;
        this.ownerName = ownerName;
        this.securityCode = hashedSecurityCode;
        this.expirationDate = expirationDate;
        this.clock = Clock.systemUTC();

    }

    public CreditCard(String hashedNumber, String ownerName, String hashedSecurityCode, LocalDate expirationDate,
                      Clock clock) {
        this.number = hashedNumber;
        this.ownerName = ownerName;
        this.securityCode = hashedSecurityCode;
        this.expirationDate = expirationDate;
        this.clock = clock;
    }


    public boolean isExpired() {
        LocalDate now = LocalDate.now(clock);
        return now.isAfter(expirationDate) || now.isEqual(expirationDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard other = (CreditCard) o;
        return number.equals(other.number) &&
                ownerName.equals(other.ownerName) &&
                securityCode.equals(other.securityCode) &&
                expirationDate.equals(other.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, ownerName, securityCode, expirationDate);
    }
}
