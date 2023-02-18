package ca.ulaval.glo4003.station.application.assemblers;

import ca.ulaval.glo4003.station.application.dtos.ChargingPointDto;
import ca.ulaval.glo4003.station.application.dtos.StationDto;
import ca.ulaval.glo4003.station.domain.Station;

import java.util.List;

public class StationAssembler {

    private final ChargingPointAssembler chargingPointAssembler = new ChargingPointAssembler();

    public StationDto toDto(Station station) {
        List<ChargingPointDto> chargingPointDtos = chargingPointAssembler.toDtos(station.getChargingPoints());
        return new StationDto(
                station.getStationId().value(),
                station.getLocation().getValue(),
                station.getName(),
                station.getCapacity(),
                station.isInMaintenance(),
                chargingPointDtos);
    }

    public List<StationDto> toDtos(List<Station> stations) {
        return stations.stream().map(this::toDto).toList();
    }

}
