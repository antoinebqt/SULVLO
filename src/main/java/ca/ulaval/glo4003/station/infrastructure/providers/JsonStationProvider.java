package ca.ulaval.glo4003.station.infrastructure.providers;

import ca.ulaval.glo4003.station.domain.StationProvider;
import ca.ulaval.glo4003.station.domain.dtos.StationCreationDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

public class JsonStationProvider implements StationProvider {

    private static final String STATIONS_CONFIG_FILE_PATH =
            "src/main/java/ca/ulaval/glo4003/station/infrastructure/config/stations.json";

    public List<StationCreationDto> getStationCreationDtos() {
        List<StationCreationDto> stationCreationDtos;

        try {
            ObjectMapper mapper = new ObjectMapper();
            stationCreationDtos = mapper.readValue(new File(STATIONS_CONFIG_FILE_PATH), new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException();
        }

        return stationCreationDtos;
    }

}
