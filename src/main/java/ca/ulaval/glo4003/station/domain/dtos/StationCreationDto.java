package ca.ulaval.glo4003.station.domain.dtos;

import ca.ulaval.glo4003.station.domain.StationLocation;
import com.fasterxml.jackson.annotation.JsonProperty;

public record StationCreationDto(

        @JsonProperty("stationId") String stationId,
        @JsonProperty("location") StationLocation location,
        @JsonProperty("name") String name,
        @JsonProperty("capacity") int capacity
) {
}
