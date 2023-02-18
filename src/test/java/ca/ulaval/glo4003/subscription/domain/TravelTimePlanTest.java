package ca.ulaval.glo4003.subscription.domain;

import ca.ulaval.glo4003.subscription.domain.exceptions.InvalidTravelTimePlanException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Duration;

class TravelTimePlanTest {

    public static final int VALID_TRAVEL_TIME = 10;
    public static final TravelTimePlan EXPECTED_PLAN = TravelTimePlan.TEN_MINUTES;
    public static final int INVALID_TRAVEL_TIME = 5020;

    @Test
    public void givenValidTravelTimeValue_whenParsingFromValue_thenReturnsAssociatedTravelTimePlan() {
        TravelTimePlan travelTimePlan = TravelTimePlan.fromValue(VALID_TRAVEL_TIME);

        Assertions.assertEquals(EXPECTED_PLAN, travelTimePlan);
    }

    @Test
    public void givenInvalidTravelTimeValue_whenParsingFromValue_thenPreventsIt() {
        Executable fromValue = () -> TravelTimePlan.fromValue(INVALID_TRAVEL_TIME);

        Assertions.assertThrows(InvalidTravelTimePlanException.class, fromValue);
    }

    @Test
    public void givenDurationGreaterThanTravelTimePlan_whenAskingIfTravelTimePlanIsGreaterOrEqual_thenReturnsFalse() {
        TravelTimePlan tenMinutesPlan = TravelTimePlan.TEN_MINUTES;

        boolean isTravelTimeGreaterOrEqual = tenMinutesPlan.isTravelTimeGreaterOrEqual(Duration.ofMinutes(20));

        Assertions.assertFalse(isTravelTimeGreaterOrEqual);
    }

    @Test
    public void givenDurationLessThanTravelTimePlan_whenAskingIfTravelTimePlanIsGreaterOrEqual_thenReturnsTrue() {
        TravelTimePlan tenMinutesPlan = TravelTimePlan.TEN_MINUTES;

        boolean isTravelTimeGreaterOrEqual = tenMinutesPlan.isTravelTimeGreaterOrEqual(Duration.ofMinutes(5));

        Assertions.assertTrue(isTravelTimeGreaterOrEqual);
    }

}