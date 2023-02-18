package ca.ulaval.glo4003.trip.domain;

import ca.ulaval.glo4003.code.application.exceptions.UnauthorizedTripException;
import ca.ulaval.glo4003.station.domain.Station;
import ca.ulaval.glo4003.station.domain.StationBuilder;
import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPointId;
import ca.ulaval.glo4003.subscription.domain.SubscriptionBuilder;
import ca.ulaval.glo4003.subscription.domain.Subscriptions;
import ca.ulaval.glo4003.subscription.domain.payment.Money;
import ca.ulaval.glo4003.trip.domain.bicycle.Bicycle;
import ca.ulaval.glo4003.trip.domain.exceptions.TripNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class TripsTest {

    public static final ChargingPointId CHARGING_POINT_ID = new ChargingPointId(2);
    public static final ChargingPointId EMPTY_CHARGING_POINT_ID = new ChargingPointId(1);
    private Trips endedTrips;
    private Station station;
    private Trips inProgressTrips;
    private Subscriptions subscriptions;
    private Bicycle bicycle;

    @BeforeEach
    public void setUp() {
        endedTrips = createEndedTrips();
        bicycle = new Bicycle();
        Trip trip = new TripBuilder().withDepartureDate(LocalDateTime.now().minusHours(2)).withBicycle(bicycle).build();
        inProgressTrips = new Trips(List.of(trip));
        station = Mockito.spy(new StationBuilder().build());
        subscriptions = Mockito.spy(new Subscriptions(List.of(new SubscriptionBuilder().build())));
    }

    @Test
    public void givenNoTripInProgress_whenStartingTrip_thenStartsTrip() {
        endedTrips.startTrip(station, CHARGING_POINT_ID);

        Assertions.assertTrue(endedTrips.hasTripInProgress());
    }

    @Test
    public void givenInProgressTrip_whenStartingTrip_thenPreventsIt() {
        Executable startTrip = () -> inProgressTrips.startTrip(station, CHARGING_POINT_ID);

        Assertions.assertThrows(UnauthorizedTripException.class, startTrip);
    }

    @Test
    public void givenNoTripInProgress_whenStartingTrip_thenRentsBicycleFromStation() {
        endedTrips.startTrip(station, CHARGING_POINT_ID);

        Mockito.verify(station).rentBicycle(CHARGING_POINT_ID);
    }

    @Test
    public void givenNoInProgressTrip_whenAskingIfHasTripInProgress_thenReturnsFalse() {
        boolean hasTripInProgress = endedTrips.hasTripInProgress();

        Assertions.assertFalse(hasTripInProgress);
    }

    @Test
    public void givenInProgressTrip_whenAskingIfHasTripInProgress_thenReturnsTrue() {
        boolean hasTripInProgress = inProgressTrips.hasTripInProgress();

        Assertions.assertTrue(hasTripInProgress);
    }

    @Test
    public void givenEndedTrips_whenGettingLastMonthTrips_thenReturnsOnlyTheLastMonthTrips() {
        List<Trip> lastMonthTrips = endedTrips.getLastMonthTrips();

        Assertions.assertEquals(1, lastMonthTrips.size());
    }

    @Test
    public void givenInProgressTrip_whenEndingTrip_thenEndsIt() {
        inProgressTrips.terminateOngoingTrip(station, EMPTY_CHARGING_POINT_ID, subscriptions);

        Assertions.assertFalse(inProgressTrips.hasTripInProgress());
    }

    @Test
    public void givenInProgressTrip_whenEndingTrip_thenUpdateSubscriptionBalance() {
        inProgressTrips.terminateOngoingTrip(station, EMPTY_CHARGING_POINT_ID, subscriptions);

        Assertions.assertNotEquals(new Money(0), subscriptions.getBalance());
    }

    @Test
    public void givenNoInProgressTrip_whenEndingTrip_thenPreventsIt() {
        Executable terminateTrip =
                () -> endedTrips.terminateOngoingTrip(station, EMPTY_CHARGING_POINT_ID, subscriptions);

        Assertions.assertThrows(TripNotFoundException.class, terminateTrip);
    }

    @Test
    public void givenInProgressTrip_whenEndingTrip_thenReturnsBicycleToStation() {
        inProgressTrips.terminateOngoingTrip(station, EMPTY_CHARGING_POINT_ID, subscriptions);

        Mockito.verify(station).returnBicycle(EMPTY_CHARGING_POINT_ID, bicycle);
    }

    private Trips createEndedTrips() {
        List<Trip> tripList = new ArrayList<>();
        tripList.add(new TripBuilder().withArrivalDate(LocalDateTime.now().minusMonths(1)).build());
        tripList.add(new TripBuilder().withArrivalDate(LocalDateTime.now()).build());

        return new Trips(tripList);
    }

}