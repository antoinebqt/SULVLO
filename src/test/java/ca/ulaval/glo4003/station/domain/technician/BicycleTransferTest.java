package ca.ulaval.glo4003.station.domain.technician;

import ca.ulaval.glo4003.station.domain.exceptions.NotEnoughBicyclesInTransferException;
import ca.ulaval.glo4003.trip.domain.bicycle.Bicycle;
import ca.ulaval.glo4003.trip.domain.bicycle.BicycleId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.UUID;

class BicycleTransferTest {
    private BicycleTransfer bicycleTransfer;
    private Bicycle bicycle;

    @BeforeEach
    public void setUpEmptyBicycleTransfer() {
        bicycle = new Bicycle(new BicycleId(UUID.randomUUID()), 100);
        bicycleTransfer = new BicycleTransfer(new ArrayList<>());
    }

    @Test
    public void whenAddingBicycle_thenTheBicycleIsAdded() {
        bicycleTransfer.addBicycle(bicycle);

        Assertions.assertTrue(bicycleTransfer.contains(bicycle));
    }

    @Test
    public void whenRemovingBicycle_thenShouldPreventRemoving() {
        Executable removeBicycle = () -> bicycleTransfer.removeBicycle();

        Assertions.assertThrows(NotEnoughBicyclesInTransferException.class, removeBicycle);
    }

    @Test
    public void givenBicyclesInBicycleTransfer_whenRemovingBicycle_thenReturnsABicycle() {
        bicycleTransfer.addBicycle(bicycle);

        Bicycle returnedBicycle = bicycleTransfer.removeBicycle();

        Assertions.assertEquals(Bicycle.class, returnedBicycle.getClass());
    }

    @Test
    public void givenBicyclesInBicycleTransfer_whenRemoving_thenRemovesOneBicycle() {
        bicycleTransfer.addBicycle(bicycle);

        bicycleTransfer.removeBicycle();

        Assertions.assertFalse(bicycleTransfer.contains(bicycle));
    }

}
