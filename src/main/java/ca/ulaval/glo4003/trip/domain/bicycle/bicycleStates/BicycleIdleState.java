package ca.ulaval.glo4003.trip.domain.bicycle.bicycleStates;

import ca.ulaval.glo4003.trip.domain.bicycle.BicycleState;
import ca.ulaval.glo4003.trip.domain.exceptions.BicycleNotChargedEnoughException;

import java.time.Clock;
import java.time.Instant;

public class BicycleIdleState implements BicycleState {

    private static final int MINIMUM_CHARGE_TO_ACTIVATE = 20;

    private final double powerWhenStateEntered;
    private final Clock clock;

    public BicycleIdleState(double powerWhenStateEntered) {
        this.powerWhenStateEntered = powerWhenStateEntered;
        this.clock = Clock.systemUTC();
    }

    public BicycleIdleState(double powerWhenStateEntered, Clock clock) {
        this.powerWhenStateEntered = powerWhenStateEntered;
        this.clock = clock;
    }

    @Override
    public double calculatePower() {
        return powerWhenStateEntered;
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
        return new BicycleChargingState(calculatePower(), Instant.now(clock));
    }

    @Override
    public BicycleState idle() {
        return this;
    }
}
