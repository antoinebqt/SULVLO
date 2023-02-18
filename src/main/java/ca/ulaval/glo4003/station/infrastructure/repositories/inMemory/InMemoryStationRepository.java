package ca.ulaval.glo4003.station.infrastructure.repositories.inMemory;

import ca.ulaval.glo4003.station.domain.Station;
import ca.ulaval.glo4003.station.domain.StationId;
import ca.ulaval.glo4003.station.domain.StationLocation;
import ca.ulaval.glo4003.station.domain.StationRepository;
import ca.ulaval.glo4003.station.domain.exceptions.StationNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryStationRepository implements StationRepository {

    Map<StationId, Station> stationsById = new HashMap<>();

    @Override
    public void persist(Station station) {
        stationsById.put(station.getStationId(), station);
    }

    @Override
    public List<Station> findAll() {
        return new ArrayList<>(stationsById.values());
    }

    @Override
    public Station findById(StationId stationId) {
        Station station = stationsById.get(stationId);
        if (station == null) {
            throw new StationNotFoundException();
        }
        return station;
    }

    @Override
    public Station findByLocation(StationLocation location) {
        return stationsById.values().stream()
                .filter(station -> station.getLocation().equals(location))
                .findFirst().orElseThrow(StationNotFoundException::new);
    }

}
