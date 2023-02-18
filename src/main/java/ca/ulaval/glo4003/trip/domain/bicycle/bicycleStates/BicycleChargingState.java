package ca.ulaval.glo4003.trip.domain.bicycle.bicycleStates;

import ca.ulaval.glo4003.trip.domain.bicycle.BicycleState;
import ca.ulaval.glo4003.trip.domain.exceptions.BicycleNotChargedEnoughException;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class BicycleChargingState implements BicycleState {

    private static final int MINIMUM_CHARGE_TO_ACTIVATE = 20;
    private static final double CHARGING_RATE_PER_SECOND = 2.0 / (10.0 * 60);

    private final double powerWhenStateEntered;
    private final Instant stateEnteredOn;
    private final Clock clock;

    public BicycleChargingState(double powerWhenStateEntered, Instant stateEnteredOn) {
        this.powerWhenStateEntered = powerWhenStateEntered;
        this.stateEnteredOn = stateEnteredOn;
        this.clock = Clock.systemUTC();
    }

    public BicycleChargingState(double powerWhenStateEntered, Instant stateEnteredOn, Clock clock) {
        this.powerWhenStateEntered = powerWhenStateEntered;
        this.stateEnteredOn = stateEnteredOn;
        this.clock = clock;
    }

    @Override
    public double calculatePower() {
        long elapsedTimeInSeconds = ChronoUnit.SECONDS.between(stateEnteredOn, Instant.now(clock));
        double chargeIncrease = CHARGING_RATE_PER_SECOND * elapsedTimeInSeconds;
        double newCharge = chargeIncrease + this.powerWhenStateEntered;
        return Math.min((newCharge), 100);
    }

    @Override
    public BicycleState activate() {
        double currentCharge = calculatePower();
        if (currentCharge < MINIMUM_CHARGE_TO_ACTIVATE) {
            throw new BicycleNotChargedEnoughException();
        }
        return new BicycleMovingState(currentCharge, Instant.now(clock), clock);
    }

    @Override
    public BicycleState recharge() {
        return this;
    }

    @Override
    public BicycleState idle() {
        return new BicycleIdleState(calculatePower(), clock);
    }
}
