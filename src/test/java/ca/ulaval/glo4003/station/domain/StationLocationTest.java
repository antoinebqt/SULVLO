package ca.ulaval.glo4003.station.domain;

import ca.ulaval.glo4003.station.domain.exceptions.InvalidStationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StationLocationTest {

    @Test
    public void givenInvalidLocation_whenGettingSemesterFromLocation_thenCannotConvertToLocation() {
        String invalidLocation = "lol";

        Executable fromLocation = () -> StationLocation.fromLocation(invalidLocation);

        assertThrows(InvalidStationException.class, fromLocation);
    }

    @Test
    public void givenValidLocation_whenFromLocation_thenReturnsAssociatedLocation() {
        String validLocation = "Peps";

        StationLocation stationLocation = StationLocation.fromLocation(validLocation);

        assertEquals(StationLocation.PEPS, stationLocation);
    }

}