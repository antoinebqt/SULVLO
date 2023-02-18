package ca.ulaval.glo4003.station.api;

import ca.ulaval.glo4003.main.api.filters.user.SecurityContextBuilder;
import ca.ulaval.glo4003.station.application.StationService;
import ca.ulaval.glo4003.station.application.dtos.BicycleTransferDto;
import ca.ulaval.glo4003.station.application.dtos.StationDto;
import ca.ulaval.glo4003.station.application.dtos.StationMaintenanceDto;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class StationResourceTest {

    public static final String USER_ID = "nice";
    public static final String STATION_ID = "1";
    public static final List<Integer> STATION_SLOTS = Arrays.asList(1, 2, 3);
    public final SecurityContext securityContext = new SecurityContextBuilder().withUserId(USER_ID).build();
    private StationResource stationResource;
    private StationService stationService;

    @BeforeEach
    public void setUp() {
        stationService = Mockito.mock(StationService.class);
        stationResource = new StationResource(stationService);
    }

    @Test
    public void whenGettingAStation_thenDelegatesToService() {
        stationResource.getStation(STATION_ID);

        Mockito.verify(stationService).getStation(STATION_ID);
    }

    @Test
    public void whenGettingAStation_thenReturnsValidResponse() {
        StationDto stationDto = createStationDto();
        Mockito.when(stationService.getStation(STATION_ID)).thenReturn(stationDto);

        Response response = stationResource.getStation(STATION_ID);

        Assertions.assertTrue(response.hasEntity());
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
    }

    @Test
    public void whenGettingAllStations_thenDelegatesToService() {
        stationResource.getAllStations();

        Mockito.verify(stationService).getAllStations();
    }

    @Test
    public void whenGettingAllStations_thenReturnsValidResponse() {
        List<StationDto> expectedStationDtos = List.of(createStationDto());
        Mockito.when(stationService.getAllStations()).thenReturn(expectedStationDtos);

        Response response = stationResource.getAllStations();

        Assertions.assertTrue(response.hasEntity());
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
    }

    @Test
    public void whenRemovingBicycles_thenDelegatesToService() {
        BicycleTransferDto bicycleTransferRequest = new BicycleTransferDto(STATION_SLOTS);

        stationResource.removeBicycles(STATION_ID, bicycleTransferRequest, securityContext);

        Mockito.verify(stationService).removeBicycles(Mockito.eq(USER_ID), Mockito.eq(STATION_ID),
                Mockito.any(BicycleTransferDto.class));
    }

    @Test
    public void whenAddingBicycles_thenDelegatesToService() {
        BicycleTransferDto bicycleTransferRequest = new BicycleTransferDto(STATION_SLOTS);

        stationResource.addBicycles(STATION_ID, bicycleTransferRequest, securityContext);

        Mockito.verify(stationService).addBicycles(Mockito.eq(USER_ID), Mockito.eq(STATION_ID),
                Mockito.any(BicycleTransferDto.class));
    }

    @Test
    public void whenRequestingAMaintenance_thenShouldDelegateToStationService() {
        stationResource.requestMaintenance(STATION_ID);

        Mockito.verify(stationService).requestMaintenance(STATION_ID);
    }

    @Test
    public void whenSettingInMaintenance_thenShouldDelegateToStationService() {
        boolean isInMaintenance = false;
        StationMaintenanceDto request = new StationMaintenanceDto(isInMaintenance);

        stationResource.setInMaintenance(STATION_ID, request);

        Mockito.verify(stationService).setInMaintenance(STATION_ID, isInMaintenance);
    }

    private StationDto createStationDto() {
        return new StationDto(STATION_ID, "PEPS", "hello", 20, false, new ArrayList<>());
    }

}