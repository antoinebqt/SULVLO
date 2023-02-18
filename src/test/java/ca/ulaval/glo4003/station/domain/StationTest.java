package ca.ulaval.glo4003.station.domain;

import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPoint;
import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPointId;
import ca.ulaval.glo4003.station.domain.exceptions.*;
import ca.ulaval.glo4003.station.domain.technician.BicycleTransfer;
import ca.ulaval.glo4003.trip.domain.bicycle.Bicycle;
import ca.ulaval.glo4003.trip.domain.bicycle.BicycleId;
import ca.ulaval.glo4003.trip.domain.bicycle.bicycleStates.BicycleChargingState;
import ca.ulaval.glo4003.trip.domain.bicycle.bicycleStates.BicycleIdleState;
import ca.ulaval.glo4003.trip.domain.bicycle.bicycleStates.BicycleMovingState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StationTest {

    private static final BicycleId A_BICYCLE_ID = new BicycleId(UUID.randomUUID());
    private static final BicycleId ANOTHER_BICYCLE_ID = new BicycleId(UUID.randomUUID());
    private static final ChargingPointId CHARGING_POINT_ID = new ChargingPointId(1);
    private static final ChargingPointId ANOTHER_CHARGING_POINT_ID = new ChargingPointId(2);

    private static final int BATTERY_POWER = 100;
    private static final Bicycle A_BICYCLE = new Bicycle(A_BICYCLE_ID, BATTERY_POWER);
    private static final Bicycle ANOTHER_BICYCLE = new Bicycle(ANOTHER_BICYCLE_ID, BATTERY_POWER);

    private Station station;

    @BeforeEach
    public void setUpStationWithChargingPoints() {
        Map<ChargingPointId, ChargingPoint> chargingPoints = createChargingPoints();
        station = new Station(new StationId("id"), StationLocation.PEPS, "peps", 2, false, chargingPoints);
    }

    @Test
    public void whenRentingABicycle_thenShouldGiveTheChosenBicycle() {
        Bicycle bicycle = station.rentBicycle(CHARGING_POINT_ID);

        assertEquals(A_BICYCLE, bicycle);
    }

    @Test
    public void whenRentingABicycle_thenShouldActivateTheChosenBicycle() {
        Bicycle bicycle = station.rentBicycle(CHARGING_POINT_ID);

        assertTrue(bicycle.getBicycleState() instanceof BicycleMovingState);
    }

    @Test
    public void whenRentingABicycle_thenShouldRemoveTheBicycle() {
        station.rentBicycle(CHARGING_POINT_ID);

        int expectedNumberOfBicyclesRemaining = 0;
        assertEquals(expectedNumberOfBicyclesRemaining, station.getNumberOfBicycles());
    }

    @Test
    public void givenInvalidChargingPoint_whenRentingBicycle_thenShouldPreventRenting() {
        ChargingPointId invalidId = new ChargingPointId(987);

        Executable rentBicycle = () -> station.rentBicycle(invalidId);

        assertThrows(StationSlotNotFoundException.class, rentBicycle);
    }

    @Test
    public void whenReturningABicycle_thenShouldAcceptIt() {
        station.returnBicycle(ANOTHER_CHARGING_POINT_ID, ANOTHER_BICYCLE);

        int expectedNumberOfBicycles = 2;
        assertEquals(expectedNumberOfBicycles, station.getNumberOfBicycles());
    }

    @Test
    public void givenInvalidChargingPoint_whenReturningABicycle_thenShouldPreventReturning() {
        ChargingPointId invalidId = new ChargingPointId(2345);

        Executable placeBicycle = () -> station.returnBicycle(invalidId, ANOTHER_BICYCLE);

        assertThrows(StationSlotNotFoundException.class, placeBicycle);
    }

    @Test
    public void whenReturningBicyclesFromTransfer_thenShouldAcceptTheBicycles() {
        BicycleTransfer bicycleTransfer = new BicycleTransfer(List.of(ANOTHER_BICYCLE));

        station.returnBicyclesFromTransfer(List.of(ANOTHER_CHARGING_POINT_ID), bicycleTransfer);

        int expectedNumberOfBicycles = 2;
        assertEquals(expectedNumberOfBicycles, station.getNumberOfBicycles());
    }

    @Test
    public void whenReturningBicyclesFromTransfer_thenShouldRemoveTheBicyclesInTransfer() {
        BicycleTransfer bicycleTransfer = Mockito.spy(new BicycleTransfer(List.of(ANOTHER_BICYCLE)));

        station.returnBicyclesFromTransfer(List.of(ANOTHER_CHARGING_POINT_ID), bicycleTransfer);

        int expectedNumberOfRemovedBicycles = 1;
        Mockito.verify(bicycleTransfer).removeBicycles(expectedNumberOfRemovedBicycles);
    }

    @Test
    public void whenReturningMoreBicyclesThanCapacity_thenShouldPreventReturning() {
        BicycleTransfer bicycleTransfer = new BicycleTransfer(List.of(ANOTHER_BICYCLE));

        Executable returnMoreBicyclesThanStationCapacity = () -> station.returnBicyclesFromTransfer(
                List.of(CHARGING_POINT_ID, ANOTHER_CHARGING_POINT_ID),
                bicycleTransfer
        );

        assertThrows(NotEnoughBicyclesInTransferException.class, returnMoreBicyclesThanStationCapacity);
    }

    @Test
    public void whenSettingInMaintenance_thenAllBicyclesShouldIdle() {
        station.setInMaintenance(true);

        assertTrue(A_BICYCLE.getBicycleState() instanceof BicycleIdleState);
    }

    @Test
    public void givenIsInMaintenance_whenSettingNotInMaintenance_thenAllBicyclesShouldCharge() {
        station.setInMaintenance(true);

        station.setInMaintenance(false);

        assertTrue(A_BICYCLE.getBicycleState() instanceof BicycleChargingState);
    }

    @Test
    public void givenIsInMaintenance_whenRemovingBicyclesForTransfer_thenShouldRemoveBicycles() {
        station.setInMaintenance(true);
        BicycleTransfer bicycleTransfer = Mockito.spy(new BicycleTransfer());

        station.removeBicyclesForTransfer(List.of(CHARGING_POINT_ID), bicycleTransfer);

        int expectedNumberOfBicyclesRemaining = 0;
        assertEquals(expectedNumberOfBicyclesRemaining, station.getNumberOfBicycles());
    }

    @Test
    public void givenIsInMaintenance_whenRemovingBicyclesForTransfer_thenShouldAddBicyclesToTransfer() {
        station.setInMaintenance(true);
        BicycleTransfer bicycleTransfer = Mockito.spy(new BicycleTransfer());

        station.removeBicyclesForTransfer(List.of(CHARGING_POINT_ID), bicycleTransfer);

        Mockito.verify(bicycleTransfer).addBicycle(A_BICYCLE);
    }

    @Test
    public void givenNotInMaintenance_whenRemovingBicyclesForTransfer_thenShouldPreventRemoving() {
        station.setInMaintenance(false);
        BicycleTransfer bicycleTransfer = new BicycleTransfer();

        Executable removeBicycles =
                () -> station.removeBicyclesForTransfer(List.of(CHARGING_POINT_ID), bicycleTransfer);

        Assertions.assertThrows(StationNotInMaintenanceException.class, removeBicycles);
    }

    @Test
    public void givenIsInMaintenance_whenRemovingMoreBicyclesThanAvailable_thenShouldPreventRemoving() {
        station.setInMaintenance(true);
        BicycleTransfer bicycleTransfer = new BicycleTransfer();

        Executable removeMoreBicyclesThanAvailable = () -> station.removeBicyclesForTransfer(
                List.of(CHARGING_POINT_ID, ANOTHER_CHARGING_POINT_ID),
                bicycleTransfer
        );

        assertThrows(BicycleNotFoundException.class, removeMoreBicyclesThanAvailable);
    }

    @Test
    public void givenIsInMaintenance_whenRentingABicycle_thenShouldPreventRenting() {
        station.setInMaintenance(true);

        Executable rentBicycle = () -> station.rentBicycle(CHARGING_POINT_ID);

        assertThrows(StationInMaintenanceException.class, rentBicycle);
    }

    @Test
    public void givenIsInMaintenance_whenReturningABicycle_thenShouldPreventReturning() {
        station.setInMaintenance(true);

        Executable returnBicycle = () -> station.returnBicycle(ANOTHER_CHARGING_POINT_ID, ANOTHER_BICYCLE);

        Assertions.assertThrows(StationInMaintenanceException.class, returnBicycle);
    }

    @Test
    public void givenIsInMaintenance_whenReturningBicycles_thenShouldPreventAdding() {
        station.setInMaintenance(true);

        Executable addBicycles = () -> station.returnBicyclesFromTransfer(
                List.of(ANOTHER_CHARGING_POINT_ID),
                new BicycleTransfer(List.of(ANOTHER_BICYCLE))
        );

        Assertions.assertThrows(StationInMaintenanceException.class, addBicycles);
    }

    private Map<ChargingPointId, ChargingPoint> createChargingPoints() {
        return Map.of(
                CHARGING_POINT_ID, new ChargingPoint(CHARGING_POINT_ID, A_BICYCLE),
                ANOTHER_CHARGING_POINT_ID, new ChargingPoint(ANOTHER_CHARGING_POINT_ID, null)
        );
    }

}