package ca.ulaval.glo4003.trip.domain.bicycle;

import ca.ulaval.glo4003.trip.domain.bicycle.bicycleStates.BicycleChargingState;

import java.time.Instant;

public class Bicycle {

    private static final int DEFAULT_BATTERY = 100;

    private final BicycleId bicycleId;
    private BicycleState state;

    public Bicycle(BicycleId bicycleId, double batteryPower) {
        this.bicycleId = bicycleId;
        this.state = new BicycleChargingState(batteryPower, Instant.now());
    }

    public Bicycle(BicycleId bicycleId, BicycleState bicycleState) {
        this.bicycleId = bicycleId;
        this.state = bicycleState;
    }

    public Bicycle() {
        this.bicycleId = new BicycleId();
        this.state = new BicycleChargingState(DEFAULT_BATTERY, Instant.now());
    }

    public void activate() {
        state = state.activate();
    }

    public void recharge() {
        state = state.recharge();
    }

    public void idle() {
        state = state.idle();
    }

    public double getBatteryPower() {
        return state.calculatePower();
    }

    public BicycleState getBicycleState() {
        return state;
    }
}
