package ca.ulaval.glo4003.trip.api;

import ca.ulaval.glo4003.main.api.filters.user.SecurityContextBuilder;
import ca.ulaval.glo4003.trip.api.assemblers.TripDtoAssembler;
import ca.ulaval.glo4003.trip.application.TripService;
import ca.ulaval.glo4003.trip.application.dtos.TripCreationDto;
import ca.ulaval.glo4003.trip.application.dtos.TripEndDto;
import jakarta.ws.rs.core.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TripResourceTest {

    private static final String USER_ID = "abcd";
    private static final String CODE = "1234";
    private static final String DEPARTURE_LOCATION = "Peps";
    private static final String LOCATION = "PEPS";
    private static final int STATION_SLOT = 2;
    private static final SecurityContext SECURITY_CONTEXT = new SecurityContextBuilder().withUserId(USER_ID).build();
    private TripResource tripResource;
    private TripService tripService;

    @BeforeEach
    public void setUp() {
        tripService = Mockito.mock(TripService.class);
        tripResource = new TripResource(tripService, new TripDtoAssembler());
    }

    @Test
    public void whenCreatingTrip_thenDelegatesToService() {
        TripCreationDto request = new TripCreationDto(CODE, DEPARTURE_LOCATION, STATION_SLOT);

        tripResource.createTrip(request, SECURITY_CONTEXT);

        Mockito.verify(tripService).createTrip(Mockito.eq(USER_ID), Mockito.argThat(t ->
                t.code().equals(CODE) && t.departureLocation().equals(DEPARTURE_LOCATION)
        ));
    }

    @Test
    public void whenEndingTrip_thenDelegatesToService() {
        TripEndDto request = new TripEndDto(LOCATION, STATION_SLOT);

        tripResource.endTrip(request, SECURITY_CONTEXT);

        Mockito.verify(tripService).endTrip(Mockito.eq(USER_ID), Mockito.argThat(r ->
                r.arrivalLocation().equals(request.arrivalLocation()) &&
                        r.chargingPointId().equals(request.chargingPointId())
        ));
    }

    @Test
    public void whenGettingHistory_thenDelegatesToService() {
        tripResource.getHistory(SECURITY_CONTEXT);

        Mockito.verify(tripService).getHistory(USER_ID);
        Mockito.verify(tripService).getSummary(USER_ID);
    }

}
