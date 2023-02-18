package ca.ulaval.glo4003.station.domain;

import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPoint;
import ca.ulaval.glo4003.station.domain.dtos.StationCreationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

class StationFactoryTest {

    public static final int STATION_CAPACITY = 5;
    private StationFactory stationFactory;

    @BeforeEach
    public void setUp() {
        StationProvider stationProvider = Mockito.mock(StationProvider.class);
        Mockito.when(stationProvider.getStationCreationDtos()).thenReturn(createStationDtos());
        stationFactory = new StationFactory(stationProvider);
    }

    @Test
    public void whenCreatingStation_thenReturnsStationsWith80PercentFullCapacity() {
        List<Station> stations = stationFactory.createStations();

        List<ChargingPoint> chargingPoints = stations.get(0).getChargingPoints();
        Assertions.assertEquals(STATION_CAPACITY, chargingPoints.size());
        Assertions.assertTrue(chargingPoints.get(0).isUsed());
        Assertions.assertTrue(chargingPoints.get(1).isUsed());
        Assertions.assertTrue(chargingPoints.get(2).isUsed());
        Assertions.assertTrue(chargingPoints.get(3).isUsed());
        Assertions.assertFalse(chargingPoints.get(4).isUsed());
    }

    private List<StationCreationDto> createStationDtos() {
        List<StationCreationDto> stationDtos = new ArrayList<>();
        stationDtos.add(new StationCreationDto("3031", StationLocation.CASAULT, "hello", STATION_CAPACITY));
        return stationDtos;
    }
}