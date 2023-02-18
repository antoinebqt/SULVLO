package ca.ulaval.glo4003.trip.domain;

import ca.ulaval.glo4003.station.domain.Station;
import ca.ulaval.glo4003.station.domain.StationBuilder;
import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPointId;
import ca.ulaval.glo4003.subscription.domain.Subscription;
import ca.ulaval.glo4003.subscription.domain.SubscriptionBuilder;
import ca.ulaval.glo4003.subscription.domain.payment.Money;
import ca.ulaval.glo4003.trip.domain.exceptions.TripAlreadyEndedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TripTest {

    public static final LocalDateTime ARRIVAL_DATE = LocalDateTime.of(2022, 1, 2, 21, 0);
    public static final LocalDateTime DEPARTURE_DATE = LocalDateTime.of(2022, 1, 2, 20, 0);
    public static final Duration EXPECTED_DURATION = Duration.ofHours(1);
    private static final ChargingPointId CHARGING_POINT_ID = new ChargingPointId(1);
    private Station station;
    private Trip endedTrip;
    private Trip inProgressTrip;
    private Subscription subscription;

    @BeforeEach
    public void setUp() {
        station = Mockito.spy(new StationBuilder().build());
        endedTrip = new TripBuilder().withDepartureDate(DEPARTURE_DATE).withArrivalDate(ARRIVAL_DATE).build();
        inProgressTrip = new TripBuilder().withBicycleInUse().build();
        subscription = new SubscriptionBuilder().build();
    }

    @Test
    public void givenAnInProgressTrip_whenIsInProgress_thenShouldReturnTrue() {
        boolean isInProgress = inProgressTrip.isInProgress();

        assertTrue(isInProgress);
    }

    @Test
    public void givenAnEndedTrip_whenIsInProgress_thenShouldReturnFalse() {
        boolean isInProgress = endedTrip.isInProgress();

        assertFalse(isInProgress);
    }

    @Test
    public void givenAnInProgressTrip_whenIsEnded_thenShouldReturnFalse() {
        boolean isEnded = inProgressTrip.isEnded();

        assertFalse(isEnded);
    }

    @Test
    public void givenAnEndedTrip_whenIsEnded_thenShouldReturnTrue() {
        boolean isEnded = endedTrip.isEnded();

        assertTrue(isEnded);
    }

    @Test
    public void givenAnInProgressTrip_whenFinishingTrip_thenReturnsBicycleToStation() {
        inProgressTrip.end(station, CHARGING_POINT_ID);

        Mockito.verify(station).returnBicycle(CHARGING_POINT_ID, inProgressTrip.getBicycle());
    }

    @Test
    public void givenAnAlreadyEndedTrip_whenFinishingTrip_thenPreventsIt() {
        Executable finishTrip = () -> endedTrip.end(station, CHARGING_POINT_ID);

        assertThrows(TripAlreadyEndedException.class, finishTrip);
    }

    @Test
    public void givenAnInProgressTrip_whenFinishingTrip_thenEndsTrip() {
        inProgressTrip.end(station, CHARGING_POINT_ID);

        assertTrue(inProgressTrip.isEnded());
    }

    @Test
    public void givenLongTripDuration_whenCalculatingTripCost_thenCalculatesExtraCharge() {
        endedTrip.calculateCost(subscription);

        assertNotEquals(new Money(0), endedTrip.getExtraCharge());
    }

    @Test
    public void givenLongTripDuration_whenCalculatingTripCost_thenUpdatesSubscriptionBalance() {
        endedTrip.calculateCost(subscription);

        assertNotEquals(new Money(0), subscription.getBalance());
    }

    @Test
    public void whenCalculatingTripDuration_thenReturnsDuration() {
        Duration duration = endedTrip.calculateDuration();

        assertEquals(EXPECTED_DURATION, duration);
    }

    @Test
    public void givenAnOldDate_whenAskingIfHasEndedAfter_thenReturnsTrue() {
        LocalDate oldDate = LocalDate.from(DEPARTURE_DATE).minusDays(1);

        boolean hasEndedAfter = endedTrip.hasEndedAfter(oldDate);

        assertTrue(hasEndedAfter);
    }

    @Test
    public void givenAFutureDate_whenAskingIfHasEndedAfter_thenReturnsFalse() {
        LocalDate futureDate = LocalDate.from(ARRIVAL_DATE).plusDays(1);

        boolean hasEndedAfter = endedTrip.hasEndedAfter(futureDate);

        assertFalse(hasEndedAfter);
    }
}
