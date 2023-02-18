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

class BicycleChargingStateTest {

    private static final double MAXIMUM_POWER = 100;
    private static final double LOW_POWER = 10;
    private static final double A_POWER_LEVEL = 34;
    private static final double EXPECTED_POWER_AFTER_10_MINUTES = 12;

    private BicycleState chargingState;

    @BeforeEach
    public void setUp() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.of("UTC"));
        chargingState = new BicycleChargingState(A_POWER_LEVEL, Instant.now(), clock);
    }

    @Test
    public void givenBicycleThatHasBeenChargingForALongTime_whenCalculatingPower_thenPowerIs100Percent() {
        BicycleChargingState state = new BicycleChargingState(LOW_POWER, Instant.MIN);

        double power = state.calculatePower();

        assertEquals(MAXIMUM_POWER, power);
    }

    @Test
    public void givenABicycleThatHasBeenChargingFor10Minutes_whenCalculatingPower_thenPowerHasIncreasedBy2Percent() {
        Instant tenMinutesAgo = Instant.now().minusSeconds(60 * 10);
        BicycleChargingState state = new BicycleChargingState(LOW_POWER, tenMinutesAgo);

        double power = state.calculatePower();

        assertEquals(EXPECTED_POWER_AFTER_10_MINUTES, power);
    }

    @Test
    public void whenRecharging_thenStateDoesNotChange() {
        BicycleState newState = chargingState.recharge();

        assertEquals(chargingState, newState);
    }

    @Test
    public void whenIdling_thenStateIsSetToIdleWithSamePowerLevel() {
        BicycleState newState = chargingState.idle();

        assertInstanceOf(BicycleIdleState.class, newState);
        assertEquals(chargingState.calculatePower(), newState.calculatePower());
    }

    @Test
    public void givenBicycleWith20PercentPowerOrMore_whenActivating_thenStateIsSetToMovingWithSamePowerLevel() {
        BicycleState newState = chargingState.activate();

        assertInstanceOf(BicycleMovingState.class, newState);
        assertEquals(chargingState.calculatePower(), newState.calculatePower());
    }

    @Test
    public void givenBicycleWithLessThan20PercentPower_whenActivating_thenCannotActivateIt() {
        BicycleChargingState state = new BicycleChargingState(LOW_POWER, Instant.now());

        Executable activate = state::activate;

        assertThrows(BicycleNotChargedEnoughException.class, activate);
    }

}