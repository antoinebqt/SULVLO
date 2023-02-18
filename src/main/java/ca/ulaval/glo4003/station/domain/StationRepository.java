package ca.ulaval.glo4003.station.domain;

import ca.ulaval.glo4003.station.domain.exceptions.StationNotFoundException;

import java.util.List;

public interface StationRepository {

    void persist(Station station);

    List<Station> findAll();

    Station findById(StationId stationId) throws StationNotFoundException;

    Station findByLocation(StationLocation departureLocation) throws StationNotFoundException;
}
