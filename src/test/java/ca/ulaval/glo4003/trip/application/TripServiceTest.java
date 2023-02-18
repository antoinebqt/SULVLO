package ca.ulaval.glo4003.trip.application;

import ca.ulaval.glo4003.code.domain.UnlockCode;
import ca.ulaval.glo4003.station.domain.Station;
import ca.ulaval.glo4003.station.domain.StationBuilder;
import ca.ulaval.glo4003.station.domain.StationLocation;
import ca.ulaval.glo4003.station.domain.StationRepository;
import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPoint;
import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPointId;
import ca.ulaval.glo4003.trip.application.assemblers.TripAssembler;
import ca.ulaval.glo4003.trip.application.dtos.SummaryDto;
import ca.ulaval.glo4003.trip.application.dtos.TripCreationDto;
import ca.ulaval.glo4003.trip.application.dtos.TripDto;
import ca.ulaval.glo4003.trip.application.dtos.TripEndDto;
import ca.ulaval.glo4003.trip.domain.Trip;
import ca.ulaval.glo4003.trip.domain.TripBuilder;
import ca.ulaval.glo4003.trip.domain.bicycle.Bicycle;
import ca.ulaval.glo4003.trip.domain.bicycle.BicycleId;
import ca.ulaval.glo4003.trip.domain.summary.SummaryFactory;
import ca.ulaval.glo4003.trip.domain.traveler.Traveler;
import ca.ulaval.glo4003.trip.domain.traveler.TravelerBuilder;
import ca.ulaval.glo4003.trip.domain.traveler.TravelerRepository;
import ca.ulaval.glo4003.user.domain.UserId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.*;

class TripServiceTest {

    public static final String USER_ID_STRING = "1234";
    public static final UserId USER_ID = new UserId(USER_ID_STRING);
    public static final UnlockCode UNLOCK_CODE = new UnlockCode("301310", LocalDateTime.now());
    public static final StationLocation STATION_LOCATION = StationLocation.CASAULT;
    public static final ChargingPointId INITIAL_CHARGING_POINT_ID = new ChargingPointId(3);
    public static final ChargingPointId RETURN_CHARGING_POINT_ID = new ChargingPointId(2);
    public static final int BATTERY_POWER = 100;
    public static final BicycleId BICYCLE_ID = new BicycleId(UUID.randomUUID());
    public static final LocalDateTime DEPARTURE_DATE = LocalDateTime.now();
    public static final LocalDateTime ARRIVAL_DATE = DEPARTURE_DATE.plusSeconds(60);
    private TripService tripService;
    private TripCreationDto tripCreationDto;
    private TripEndDto tripEndDto;
    private Station station;
    private StationRepository stationRepository;
    private Traveler traveler;
    private TravelerRepository travelerRepository;

    @BeforeEach
    public void setUp() {
        tripCreationDto = new TripCreationDto(UNLOCK_CODE.getValue(), STATION_LOCATION.getValue(),
                INITIAL_CHARGING_POINT_ID.getValue());
        tripEndDto =
                new TripEndDto(STATION_LOCATION.getValue(), RETURN_CHARGING_POINT_ID.getValue());

        station = createStation();
        stationRepository = Mockito.mock(StationRepository.class);
        Mockito.when(stationRepository.findByLocation(STATION_LOCATION)).thenReturn(station);

        TripAssembler tripAssembler = new TripAssembler();
        SummaryFactory summaryFactory = new SummaryFactory();
        travelerRepository = Mockito.mock(TravelerRepository.class);

        tripService = new TripService(stationRepository, travelerRepository, tripAssembler, summaryFactory);
    }

    @Test
    public void givenTravelerWithEndedTrips_whenCreatingTrip_thenTravelerShouldBeTravelling() {
        givenTravelerWithEndedTrips();

        tripService.createTrip(USER_ID_STRING, tripCreationDto);

        Assertions.assertTrue(traveler.isTravelling());
    }

    @Test
    public void givenTravelerWithEndedTrips_whenCreatingTrip_thenTravelerIsSavedInRepository() {
        givenTravelerWithEndedTrips();

        tripService.createTrip(USER_ID_STRING, tripCreationDto);

        Mockito.verify(travelerRepository).persist(traveler);
    }

    @Test
    public void givenTravelerWithEndedTrips_whenCreatingTrip_thenStationIsSavedInRepository() {
        givenTravelerWithEndedTrips();

        tripService.createTrip(USER_ID_STRING, tripCreationDto);

        Mockito.verify(stationRepository).persist(station);
    }

    @Test
    public void givenTravelerWithEndedTrips_whenCreatingTrip_thenBicycleIsRentedFromStation() {
        givenTravelerWithEndedTrips();

        tripService.createTrip(USER_ID_STRING, tripCreationDto);

        Assertions.assertFalse(station.getChargingPoints().get(0).isUsed());
    }

    @Test
    public void givenTravelerWithInProgressTrip_whenEndingTrip_thenTripIsEnded() {
        givenTravelerWithInProgressTrips();

        tripService.endTrip(USER_ID_STRING, tripEndDto);

        Assertions.assertFalse(traveler.isTravelling());
    }

    @Test
    public void givenTravelerWithInProgressTrip_whenEndingTrip_thenBicycleIsReturnedToStation() {
        givenTravelerWithInProgressTrips();

        tripService.endTrip(USER_ID_STRING, tripEndDto);

        Assertions.assertTrue(station.getChargingPoints().get(1).isUsed());
    }

    @Test
    public void givenTravelerWithInProgressTrip_whenEndingTrip_thenTravelerIsSavedInRepository() {
        givenTravelerWithInProgressTrips();

        tripService.endTrip(USER_ID_STRING, tripEndDto);

        Mockito.verify(travelerRepository).persist(traveler);
    }

    @Test
    public void givenTravelerWithInProgressTrip_whenEndingTrip_thenStationIsSavedInRepository() {
        givenTravelerWithInProgressTrips();

        tripService.endTrip(USER_ID_STRING, tripEndDto);

        Mockito.verify(stationRepository).persist(station);
    }

    @Test
    public void givenTravelerWithEndedTrips_whenGettingLastMonthHistory_thenReturnsTheTrips() {
        givenTravelerWithEndedTrips();

        List<TripDto> tripsDto = tripService.getHistory(USER_ID.getValue());

        Assertions.assertEquals(1, tripsDto.size());
    }

    @Test
    public void givenTravelerWithEndedTrips_whenGettingSummary_thenReturnsSummaryOfTrips() {
        givenTravelerWithEndedTrips();

        SummaryDto summaryDto = tripService.getSummary(USER_ID.getValue());

        Assertions.assertEquals(1, summaryDto.numberOfTrips());
    }

    private Station createStation() {
        Map<ChargingPointId, ChargingPoint> chargingPoints = new HashMap<>();
        Bicycle bicycle = new Bicycle(BICYCLE_ID, BATTERY_POWER);
        chargingPoints.put(INITIAL_CHARGING_POINT_ID, new ChargingPoint(INITIAL_CHARGING_POINT_ID, bicycle));
        chargingPoints.put(RETURN_CHARGING_POINT_ID, new ChargingPoint(RETURN_CHARGING_POINT_ID, null));

        return new StationBuilder().withLocation(STATION_LOCATION).withChargingPoints(chargingPoints).build();
    }

    private void givenTravelerWithEndedTrips() {
        List<Trip> trips = new ArrayList<>();
        trips.add(new TripBuilder().withArrivalDate(ARRIVAL_DATE).withArrivalStation(STATION_LOCATION).build());

        traveler = new TravelerBuilder().withUserId(USER_ID).withTrips(trips).withActiveSubscription()
                .withUnlockCode(UNLOCK_CODE).build();
        Mockito.when(travelerRepository.findById(USER_ID)).thenReturn(traveler);
    }

    private void givenTravelerWithInProgressTrips() {
        List<Trip> trips = new ArrayList<>();
        trips.add(new TripBuilder().build());

        traveler = new TravelerBuilder().withUserId(USER_ID).withUnlockCode(UNLOCK_CODE).withActiveSubscription()
                .withTrips(trips).build();
        Mockito.when(travelerRepository.findById(USER_ID)).thenReturn(traveler);
    }
}
