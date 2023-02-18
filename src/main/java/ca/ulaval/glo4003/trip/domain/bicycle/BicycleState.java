package ca.ulaval.glo4003.trip.domain.bicycle;

public interface BicycleState {
    double calculatePower();

    BicycleState activate();

    BicycleState recharge();

    BicycleState idle();
}
