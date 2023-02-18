package ca.ulaval.glo4003.station.domain;

import ca.ulaval.glo4003.station.domain.dtos.StationCreationDto;

import java.util.List;

public interface StationProvider {

    List<StationCreationDto> getStationCreationDtos();
}
