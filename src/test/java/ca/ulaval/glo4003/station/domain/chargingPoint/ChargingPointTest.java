package ca.ulaval.glo4003.station.domain.chargingPoint;

import ca.ulaval.glo4003.station.domain.exceptions.BicycleAlreadyExistsException;
import ca.ulaval.glo4003.station.domain.exceptions.BicycleNotFoundException;
import ca.ulaval.glo4003.trip.domain.bicycle.Bicycle;
import ca.ulaval.glo4003.trip.domain.bicycle.BicycleId;
import ca.ulaval.glo4003.trip.domain.bicycle.bicycleStates.BicycleChargingState;
import ca.ulaval.glo4003.trip.domain.bicycle.bicycleStates.BicycleIdleState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ChargingPointTest {

    private static final ChargingPointId A_CHARGING_POINT_ID = new ChargingPointId(804932);

    @Test
    public void givenARechargingBicycle_whenCheckingIfChargingPointIsUsed_thenShouldBeUsed() {
        Bicycle bicycle = createBicycle();
        ChargingPoint chargingPoint = new ChargingPoint(A_CHARGING_POINT_ID, bicycle);

        boolean isChargingPointUsed = chargingPoint.isUsed();

        assertTrue(isChargingPointUsed);
    }

    @Test
    public void givenARechargingBicycle_whenRemovingTheBicycle_thenShouldBeAvailable() {
        Bicycle bicycle = createBicycle();
        ChargingPoint chargingPoint = new ChargingPoint(A_CHARGING_POINT_ID, bicycle);

        chargingPoint.removeBicycle();

        assertFalse(chargingPoint.isUsed());
    }

    @Test
    public void givenARechargingBicycle_whenRemovingTheBicycle_thenShouldGiveTheBicycle() {
        Bicycle bicycle = createBicycle();
        ChargingPoint chargingPoint = new ChargingPoint(A_CHARGING_POINT_ID, bicycle);

        Bicycle removedBicycle = chargingPoint.removeBicycle();

        assertEquals(bicycle, removedBicycle);
    }

    @Test
    public void givenARechargingBicycle_whenPlacingAnotherBicycle_thenShouldPreventThePlacement() {
        Bicycle bicycle = createBicycle();
        Bicycle anotherBicycle = createBicycle();
        ChargingPoint chargingPoint = new ChargingPoint(A_CHARGING_POINT_ID, bicycle);

        Executable placeBicycle = () -> chargingPoint.placeBicycle(anotherBicycle);

        assertThrows(BicycleAlreadyExistsException.class, placeBicycle);
    }

    @Test
    public void givenARechargingBicycle_whenIdleBicycle_thenShouldPlaceTheBicycleInIdle() {
        Bicycle bicycle = createBicycle();
        ChargingPoint chargingPoint = new ChargingPoint(A_CHARGING_POINT_ID, bicycle);

        chargingPoint.idleBicycle();

        assertEquals(BicycleIdleState.class, bicycle.getBicycleState().getClass());
    }

    @Test
    public void givenAnIdleBicycle_whenRechargingTheBicycle_thenShouldPlaceTheBicycleInRecharge() {
        Bicycle bicycleInIdle = createBicycleInIdle();
        ChargingPoint chargingPoint = new ChargingPoint(A_CHARGING_POINT_ID, bicycleInIdle);

        chargingPoint.rechargeBicycle();

        assertEquals(BicycleChargingState.class, bicycleInIdle.getBicycleState().getClass());
    }

    @Test
    public void givenNoBicycle_whenPlacingABicycle_thenShouldBeUsed() {
        ChargingPoint chargingPoint = new ChargingPoint(A_CHARGING_POINT_ID, null);
        Bicycle bicycle = createBicycle();

        chargingPoint.placeBicycle(bicycle);

        assertTrue(chargingPoint.isUsed());
    }

    @Test
    public void givenABicycle_whenGettingBicycleCharge_thenShouldCalculateCharge() {
        Bicycle bicycle = createBicycle();
        ChargingPoint chargingPoint = new ChargingPoint(A_CHARGING_POINT_ID, bicycle);

        double bicycleCharge = chargingPoint.getBicycleCharge();

        assertEquals(bicycle.getBatteryPower(), bicycleCharge);
    }

    @Test
    public void givenNoBicycle_whenGettingBicycleCharge_thenShouldNotReturnCharge() {
        ChargingPoint chargingPoint = new ChargingPoint(A_CHARGING_POINT_ID, null);

        Executable getCharge = chargingPoint::getBicycleCharge;

        assertThrows(BicycleNotFoundException.class, getCharge);
    }

    private Bicycle createBicycle() {
        return new Bicycle(new BicycleId(), new BicycleChargingState(70.0, Instant.now()));
    }

    private Bicycle createBicycleInIdle() {
        return new Bicycle(new BicycleId(), new BicycleIdleState(70.0));
    }

}
