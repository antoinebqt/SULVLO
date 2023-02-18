package ca.ulaval.glo4003.station.domain;

import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPoint;
import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPointId;
import ca.ulaval.glo4003.station.domain.dtos.StationCreationDto;
import ca.ulaval.glo4003.trip.domain.bicycle.Bicycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StationFactory {

    private static final double CAPACITY_MULTIPLIER = 0.8;

    private final StationProvider stationProvider;

    public StationFactory(StationProvider stationProvider) {
        this.stationProvider = stationProvider;
    }

    public List<Station> createStations() {
        List<StationCreationDto> dtos = stationProvider.getStationCreationDtos();

        List<Station> stations = new ArrayList<>();
        for (StationCreationDto dto : dtos) {
            Map<ChargingPointId, ChargingPoint> chargingPoints = createChargingPoints(dto.capacity());
            Station station = new Station(new StationId(
                    dto.stationId()), dto.location(), dto.name(), dto.capacity(), false, chargingPoints
            );
            stations.add(station);
        }
        return stations;
    }

    private Map<ChargingPointId, ChargingPoint> createChargingPoints(int capacity) {
        Map<ChargingPointId, ChargingPoint> chargingPoints = new HashMap<>(capacity);
        int numberOfBikes = (int) Math.floor(CAPACITY_MULTIPLIER * capacity);
        for (int i = 1; i <= numberOfBikes; i++) {
            ChargingPoint chargingPoint = new ChargingPoint(new ChargingPointId(i), new Bicycle());
            chargingPoints.put(chargingPoint.getId(), chargingPoint);
        }
        for (int i = numberOfBikes + 1; i <= capacity; i++) {
            ChargingPoint chargingPoint = new ChargingPoint(new ChargingPointId(i), null);
            chargingPoints.put(chargingPoint.getId(), chargingPoint);
        }
        return chargingPoints;
    }
}
