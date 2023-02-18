package ca.ulaval.glo4003.trip.domain.summary;

import ca.ulaval.glo4003.station.domain.StationLocation;
import ca.ulaval.glo4003.trip.domain.Trip;
import ca.ulaval.glo4003.trip.domain.TripBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class SummaryFactoryTest {


    private SummaryFactory summaryFactory;

    @BeforeEach
    public void setUp() {
        summaryFactory = new SummaryFactory();
    }

    @Test
    public void givenNoTrips_whenCreatingSummary_thenReturnsNothing() {
        Assertions.assertNull(summaryFactory.createSummary(new ArrayList<>()));
    }

    @Test
    public void givenTrips_whenCreatingSummary_thenReturnsAppropriateSummary() {
        List<Trip> trips = givenTrips();
        Summary summary = summaryFactory.createSummary(trips);

        Assertions.assertEquals(3, summary.getNumberOfTrips());
        Assertions.assertEquals(40, summary.getAverageTravelTime().toSeconds());
        Assertions.assertEquals(StationLocation.CASAULT, summary.getFavouriteStation());
        Assertions.assertEquals(120, summary.getTotalTravelTime().toSeconds());
    }

    private List<Trip> givenTrips() {
        List<Trip> trips = new ArrayList<>();
        trips.add(new TripBuilder().withArrivalStation(StationLocation.CASAULT)
                .withArrivalDate(LocalDateTime.now().plusSeconds(30)).build());
        trips.add(new TripBuilder().withArrivalStation(StationLocation.CASAULT)
                .withArrivalDate(LocalDateTime.now().plusSeconds(50)).build());
        trips.add(new TripBuilder().withArrivalStation(StationLocation.PEPS)
                .withArrivalDate(LocalDateTime.now().plusSeconds(40)).build());

        return trips;
    }

}