package ca.ulaval.glo4003.station.application.dtos;

public class ChargingPointDto {

    public final int id;
    public final BicycleDto bicycle;

    public ChargingPointDto(int chargingPointId, BicycleDto bicycle) {
        this.id = chargingPointId;
        this.bicycle = bicycle;
    }

}
