package ca.ulaval.glo4003.station.domain.chargingPoint;

import ca.ulaval.glo4003.station.domain.exceptions.BicycleAlreadyExistsException;
import ca.ulaval.glo4003.station.domain.exceptions.BicycleNotFoundException;
import ca.ulaval.glo4003.trip.domain.bicycle.Bicycle;


public class ChargingPoint {

    private final ChargingPointId chargingPointId;
    private Bicycle bicycle;

    public ChargingPoint(ChargingPointId chargingPointId, Bicycle bicycle) {
        this.chargingPointId = chargingPointId;
        this.bicycle = bicycle;
    }

    public ChargingPointId getId() {
        return chargingPointId;
    }

    public void idleBicycle() {
        if (isUsed()) {
            bicycle.idle();
        }
    }

    public void rechargeBicycle() {
        if (isUsed()) {
            bicycle.recharge();
        }
    }

    public double getBicycleCharge() {
        if (isAvailable()) {
            throw new BicycleNotFoundException();
        }
        return bicycle.getBatteryPower();
    }

    public Bicycle removeBicycle() {
        if (isAvailable()) {
            throw new BicycleNotFoundException();
        }
        Bicycle removedBicycle = this.bicycle;
        this.bicycle = null;
        return removedBicycle;
    }

    public void placeBicycle(Bicycle bicycle) {
        if (isUsed()) {
            throw new BicycleAlreadyExistsException();
        }
        this.bicycle = bicycle;
        rechargeBicycle();
    }

    private boolean isAvailable() {
        return bicycle == null;
    }

    public boolean isUsed() {
        return !isAvailable();
    }
}
