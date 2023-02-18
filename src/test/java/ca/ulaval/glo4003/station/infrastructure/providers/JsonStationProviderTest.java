package ca.ulaval.glo4003.station.infrastructure.providers;

import ca.ulaval.glo4003.station.domain.dtos.StationCreationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class JsonStationProviderTest {

    private JsonStationProvider jsonStationProvider;

    @BeforeEach
    public void setUp() {
        jsonStationProvider = new JsonStationProvider();
    }

    @Test
    public void whenGettingStations_thenReturnsStationDtos() {
        List<StationCreationDto> stationCreationDtos = jsonStationProvider.getStationCreationDtos();

        Assertions.assertFalse(stationCreationDtos.isEmpty());
    }
}