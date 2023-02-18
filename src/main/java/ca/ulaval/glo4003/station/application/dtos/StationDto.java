package ca.ulaval.glo4003.station.application.dtos;

import java.util.List;

public record StationDto(String id,
                         String location,
                         String name,
                         int capacity,
                         boolean inMaintenance,
                         List<ChargingPointDto> chargingPoints) {
}
