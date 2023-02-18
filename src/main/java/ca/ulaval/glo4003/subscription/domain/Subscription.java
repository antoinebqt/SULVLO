package ca.ulaval.glo4003.subscription.domain;

import ca.ulaval.glo4003.subscription.domain.payment.Invoice;
import ca.ulaval.glo4003.subscription.domain.payment.Money;
import ca.ulaval.glo4003.subscription.domain.payment.PaymentClient;
import ca.ulaval.glo4003.subscription.domain.payment.creditCard.CreditCard;
import ca.ulaval.glo4003.subscription.domain.semester.Semester;

import java.time.Duration;

public abstract class Subscription {
    protected static final int SECONDS_PER_MINUTE = 60;

    protected final Semester semester;
    protected final TravelTimePlan travelTimePlan;

    protected final PaymentClient paymentClient;
    protected Money balance;

    public Subscription(Semester semester, TravelTimePlan travelTimePlan, PaymentClient paymentClient) {
        this.semester = semester;
        this.travelTimePlan = travelTimePlan;
        this.paymentClient = paymentClient;
        this.balance = new Money(0);
    }

    public abstract boolean isActive();

    public Invoice purchase(CreditCard creditCard) {
        paymentClient.debit(travelTimePlan.getPrice(), creditCard);
        return new Invoice(travelTimePlan.getPrice());
    }

    public Money calculateExtraCharge(Duration tripDuration) {
        if (travelTimePlan.isTravelTimeGreaterOrEqual(tripDuration)) {
            return new Money(0);
        }

        double timeExceededInSeconds = tripDuration.minusSeconds(travelTimePlan.getTravelTimeInSeconds()).toSeconds();
        double extraChargeRate = travelTimePlan.getExtraChargeRate();

        Money extraCharge = timeExceededInSeconds % SECONDS_PER_MINUTE < 15 ?
                new Money(Math.floor(timeExceededInSeconds / SECONDS_PER_MINUTE) * extraChargeRate) :
                new Money(Math.ceil(timeExceededInSeconds / SECONDS_PER_MINUTE) * extraChargeRate);

        this.balance = balance.add(extraCharge);
        return extraCharge;
    }

    public Money getBalance() {
        return balance;
    }

    public Semester getSemester() {
        return semester;
    }

    public TravelTimePlan getTravelTimePlan() {
        return travelTimePlan;
    }

    public boolean matchesSemester(Subscription subscription) {
        return this.semester.equals(subscription.semester);
    }
}
