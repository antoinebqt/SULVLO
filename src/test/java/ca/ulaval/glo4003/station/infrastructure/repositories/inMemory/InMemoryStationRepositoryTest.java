package ca.ulaval.glo4003.station.infrastructure.repositories.inMemory;

import ca.ulaval.glo4003.station.domain.*;
import ca.ulaval.glo4003.station.domain.exceptions.StationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryStationRepositoryTest {

    private static final StationId ANOTHER_ID = new StationId("645");
    private static final StationLocation STATION_LOCATION = StationLocation.fromLocation("Vachon");
    private static final Station STATION = new StationBuilder().withLocation(STATION_LOCATION).build();
    private static final Station ANOTHER_STATION = new StationBuilder().withId(ANOTHER_ID).build();

    public StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        stationRepository = new InMemoryStationRepository();
    }

    @Test
    void whenPersistingStation_thenShouldAcceptIt() {
        stationRepository.persist(STATION);

        Station station = stationRepository.findById(STATION.getStationId());
        assertEquals(STATION, station);
    }

    @Test
    void givenStationsAlreadySaved_whenFindAll_thenReturnsAllSavedStations() {
        stationRepository.persist(STATION);
        stationRepository.persist(ANOTHER_STATION);

        List<Station> stations = stationRepository.findAll();

        assertEquals(2, stations.size());
    }

    @Test
    void givenStationIdThatDoesNotExist_whenFindById_thenStationCannotBeFound() {
        StationId idThatDoesNotExist = new StationId("12345");

        Executable findStation = () -> stationRepository.findById(idThatDoesNotExist);

        assertThrows(StationNotFoundException.class, findStation);
    }

    @Test
    void givenStationLocationThatExists_whenFindByLocation_thenReturnTheStation() {
        stationRepository.persist(STATION);

        Station found = stationRepository.findByLocation(STATION_LOCATION);

        assertEquals(STATION, found);
    }

    @Test
    void givenStationLocationThatDoesNotExist_whenFindByLocation_thenStationCannotBeFound() {
        StationLocation stationLocationThatDoesNotExistInRepository = StationLocation.CASAULT;

        Executable findStation = () -> stationRepository.findByLocation(stationLocationThatDoesNotExistInRepository);

        assertThrows(StationNotFoundException.class, findStation);
    }
}
