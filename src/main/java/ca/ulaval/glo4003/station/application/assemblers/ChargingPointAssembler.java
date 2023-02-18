package ca.ulaval.glo4003.station.application.assemblers;

import ca.ulaval.glo4003.station.application.dtos.BicycleDto;
import ca.ulaval.glo4003.station.application.dtos.ChargingPointDto;
import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPoint;

import java.util.List;

public class ChargingPointAssembler {

    public ChargingPointDto toDto(ChargingPoint chargingPoint) {
        BicycleDto bicycleDto = chargingPoint.isUsed() ? new BicycleDto(chargingPoint.getBicycleCharge()) : null;
        return new ChargingPointDto(chargingPoint.getId().getValue(), bicycleDto);
    }

    public List<ChargingPointDto> toDtos(List<ChargingPoint> chargingPoints) {
        return chargingPoints.stream().map(this::toDto).toList();
    }

}
