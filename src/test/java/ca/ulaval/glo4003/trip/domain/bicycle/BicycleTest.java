package ca.ulaval.glo4003.trip.domain.bicycle;

import ca.ulaval.glo4003.trip.domain.bicycle.bicycleStates.BicycleChargingState;
import ca.ulaval.glo4003.trip.domain.bicycle.bicycleStates.BicycleIdleState;
import ca.ulaval.glo4003.trip.domain.bicycle.bicycleStates.BicycleMovingState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BicycleTest {
    private static final BicycleId A_BICYCLE_ID = new BicycleId(UUID.randomUUID());
    private static final int EXPECTED_POWER = 100;
    private Bicycle bicycle;
    private BicycleState state;

    @BeforeEach
    public void setUpBicycle() {
        state = Mockito.mock(BicycleState.class);
        bicycle = new Bicycle();
    }

    @Test
    public void whenInitializing_thenShouldHave100PercentPower() {
        assertEquals(EXPECTED_POWER, bicycle.getBatteryPower());
    }

    @Test
    public void whenInitializing_thenShouldBeInChargingState() {
        assertTrue(bicycle.getBicycleState() instanceof BicycleChargingState);
    }

    @Test
    public void whenCharging_thenShouldBeInChargingState() {
        bicycle.recharge();

        assertTrue(bicycle.getBicycleState() instanceof BicycleChargingState);
    }

    @Test
    public void whenActivating_thenShouldBeInActiveState() {
        bicycle.activate();

        assertTrue(bicycle.getBicycleState() instanceof BicycleMovingState);
    }

    @Test
    public void whenIdle_thenShouldBeInIdleState() {
        bicycle.idle();

        assertTrue(bicycle.getBicycleState() instanceof BicycleIdleState);
    }

    @Test
    public void whenGettingPower_thenShouldDelegateToState() {
        Bicycle bicycle = new Bicycle(A_BICYCLE_ID, state);

        bicycle.getBatteryPower();

        Mockito.verify(state).calculatePower();
    }

}
