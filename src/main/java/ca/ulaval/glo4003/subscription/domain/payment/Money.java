package ca.ulaval.glo4003.subscription.domain.payment;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {

    private final BigDecimal amount;

    public Money(double amount) {
        this.amount = BigDecimal.valueOf(amount);
    }

    private Money(BigDecimal amount) {
        this.amount = amount;
    }

    public Money add(Money money) {
        BigDecimal calculatedSum = amount.add(money.amount);
        return new Money(calculatedSum);
    }

    public double toDouble() {
        double value = amount.doubleValue();
        return Math.round(value * 100) / 100.00;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money other = (Money) o;
        return amount.equals(other.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

}

