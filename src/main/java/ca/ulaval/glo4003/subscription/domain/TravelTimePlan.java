package ca.ulaval.glo4003.subscription.domain;

import ca.ulaval.glo4003.subscription.domain.exceptions.InvalidTravelTimePlanException;
import ca.ulaval.glo4003.subscription.domain.payment.Money;

import java.time.Duration;

public enum TravelTimePlan {
    TEN_MINUTES(Duration.ofMinutes(10), 0.05, new Money(30)),
    THIRTY_MINUTES(Duration.ofMinutes(30), 0.1, new Money(50)),
    UNLIMITED(Duration.ofMinutes(Integer.MAX_VALUE), 0, new Money(0));

    private final Duration travelTimeInMinutes;
    private final double extraChargeRate;
    private final Money price;

    TravelTimePlan(Duration travelTimeInMinutes, double extraChargeRate, Money price) {
        this.travelTimeInMinutes = travelTimeInMinutes;
        this.extraChargeRate = extraChargeRate;
        this.price = price;
    }

    public static TravelTimePlan fromValue(int travelTimeValue) {
        for (TravelTimePlan travelTimePlan : values()) {
            if (travelTimeValue == travelTimePlan.travelTimeInMinutes.toMinutes()) {
                return travelTimePlan;
            }
        }
        throw new InvalidTravelTimePlanException();
    }

    public long getTravelTimeInSeconds() {
        return travelTimeInMinutes.toSeconds();
    }

    public double getExtraChargeRate() {
        return extraChargeRate;
    }

    public Money getPrice() {
        return price;
    }

    public boolean isTravelTimeGreaterOrEqual(Duration duration) {
        return travelTimeInMinutes.toSeconds() >= duration.getSeconds();
    }
}
