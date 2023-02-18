package ca.ulaval.glo4003.trip.domain.bicycle.bicycleStates;

import ca.ulaval.glo4003.trip.domain.bicycle.BicycleState;
import ca.ulaval.glo4003.trip.domain.exceptions.BicycleNotChargedEnoughException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class BicycleIdleStateTest {

    public static final int LOW_POWER = 10;
    private static final double BICYCLE_POWER = 50;
    private static final double A_POWER_LEVEL = 30;
    private static final double EXPECTED_POWER_AFTER_10_MINUTES = 50;
    private BicycleIdleState idleState;

    @BeforeEach
    public void setUp() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.of("UTC"));
        idleState = new BicycleIdleState(A_POWER_LEVEL, clock);
    }

    @Test
    public void givenBicycleThatHasBeenIdleForALongTime_whenCalculatingPower_thenPowerDidNotChange() {
        Clock clock = Clock.fixed(Instant.MIN, ZoneId.of("UTC"));
        BicycleIdleState state = new BicycleIdleState(BICYCLE_POWER, clock);

        double power = state.calculatePower();

        assertEquals(EXPECTED_POWER_AFTER_10_MINUTES, power);
    }

    @Test
    public void whenRecharging_thenStateIsSetToChargingWithSamePowerLevel() {
        BicycleState newState = idleState.recharge();

        assertInstanceOf(BicycleChargingState.class, newState);
        assertEquals(idleState.calculatePower(), newState.calculatePower());
    }

    @Test
    public void whenIdling_thenStateDoesNotChange() {
        BicycleState newState = idleState.idle();

        assertEquals(idleState, newState);
    }

    @Test
    public void givenBicycleWith20PercentPowerOrMore_whenActivating_thenStateIsSetToMovingWithSamePowerLevel() {
        BicycleState newState = idleState.activate();

        assertInstanceOf(BicycleMovingState.class, newState);
        assertEquals(idleState.calculatePower(), newState.calculatePower());
    }

    @Test
    public void givenBicycleWithLessThan20PercentPower_whenActivating_thenCannotActivateIt() {
        BicycleIdleState state = new BicycleIdleState(LOW_POWER);

        Executable activate = state::activate;

        assertThrows(BicycleNotChargedEnoughException.class, activate);
    }

}