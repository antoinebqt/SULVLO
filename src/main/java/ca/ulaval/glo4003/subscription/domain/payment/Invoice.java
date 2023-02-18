package ca.ulaval.glo4003.subscription.domain.payment;

import java.util.Objects;

public class Invoice {

    private final Money cost;

    public Invoice(Money cost) {
        this.cost = cost;
    }

    public Money getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice other = (Invoice) o;
        return cost.equals(other.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cost);
    }
}
