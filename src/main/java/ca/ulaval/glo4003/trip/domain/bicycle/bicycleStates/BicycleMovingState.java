package ca.ulaval.glo4003.trip.domain.bicycle.bicycleStates;

import ca.ulaval.glo4003.trip.domain.bicycle.BicycleState;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class BicycleMovingState implements BicycleState {

    private static final double CHARGE_LOST_PER_SECOND = 5.0 / (10.0 * 60);

    private final double powerWhenStateEntered;
    private final Instant stateEnteredOn;
    private final Clock clock;

    public BicycleMovingState(double powerWhenStateEntered, Instant stateEnteredOn) {
        this.powerWhenStateEntered = powerWhenStateEntered;
        this.stateEnteredOn = stateEnteredOn;
        this.clock = Clock.systemUTC();
    }

    public BicycleMovingState(double powerWhenStateEntered, Instant stateEnteredOn, Clock clock) {
        this.powerWhenStateEntered = powerWhenStateEntered;
        this.stateEnteredOn = stateEnteredOn;
        this.clock = clock;
    }

    @Override
    public double calculatePower() {
        long elapsedTimeInSeconds = ChronoUnit.SECONDS.between(stateEnteredOn, Instant.now(clock));
        double chargeDecrease = CHARGE_LOST_PER_SECOND * elapsedTimeInSeconds;
        double newCharge = this.powerWhenStateEntered - chargeDecrease;
        return Math.max((newCharge), 0);
    }

    @Override
    public BicycleState activate() {
        return this;
    }

    @Override
    public BicycleState recharge() {
        return new BicycleChargingState(calculatePower(), Instant.now(clock));
    }

    @Override
    public BicycleState idle() {
        return new BicycleIdleState(calculatePower());
    }
}
