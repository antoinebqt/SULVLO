package ca.ulaval.glo4003.trip.domain.bicycle.bicycleStates;

import ca.ulaval.glo4003.trip.domain.bicycle.BicycleState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class BicycleMovingStateTest {

    private static final double BICYCLE_POWER = 50;
    private static final double MINIMUM_POWER = 0;
    private static final double A_POWER_LEVEL = 30;
    private static final double EXPECTED_POWER_AFTER_10_MINUTES = 45;

    private BicycleMovingState movingState;

    @BeforeEach
    public void setUp() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.of("UTC"));
        movingState = new BicycleMovingState(A_POWER_LEVEL, Instant.now(), clock);
    }

    @Test
    public void givenBicycleThatHasBeenUsedForALongTime_whenCalculatingPower_thenPowerIs0Percent() {
        BicycleMovingState state = new BicycleMovingState(BICYCLE_POWER, Instant.MIN);

        double power = state.calculatePower();

        assertEquals(MINIMUM_POWER, power);
    }

    @Test
    public void givenABicycleThatHasBeenUsedFor10Minutes_whenCalculatingPower_thenPowerHasDecreasedBy5Percent() {
        Instant tenMinutesAgo = Instant.now().minusSeconds(60 * 10);
        BicycleMovingState state = new BicycleMovingState(BICYCLE_POWER, tenMinutesAgo);

        double power = state.calculatePower();

        assertEquals(EXPECTED_POWER_AFTER_10_MINUTES, power);
    }

    @Test
    public void whenRecharging_thenStateIsSetToChargingWithSamePowerLevel() {
        BicycleState newState = movingState.recharge();

        assertInstanceOf(BicycleChargingState.class, newState);
        assertEquals(movingState.calculatePower(), newState.calculatePower());
    }

    @Test
    public void whenIdling_thenStateIsSetToIdleWithSamePowerLevel() {
        BicycleState newState = movingState.idle();

        assertInstanceOf(BicycleIdleState.class, newState);

        assertEquals(movingState.calculatePower(), newState.calculatePower());
    }

    @Test
    public void whenActivating_thenStateDoesNotChange() {
        BicycleState newState = movingState.activate();

        assertEquals(movingState, newState);
    }

}