package ca.ulaval.glo4003.station.domain;

import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPoint;
import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPointId;
import ca.ulaval.glo4003.trip.domain.bicycle.Bicycle;
import ca.ulaval.glo4003.trip.domain.bicycle.BicycleId;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StationBuilder {

    private final String name;
    private final int maxCapacity;
    private StationLocation stationLocation;
    private Map<ChargingPointId, ChargingPoint> chargingPoints;
    private StationId stationId;
    private boolean inMaintenance;

    public StationBuilder() {
        this.stationId = new StationId("324");
        this.maxCapacity = 20;
        this.name = "PEPS";
        this.stationLocation = StationLocation.PEPS;
        this.inMaintenance = false;
        this.chargingPoints = createChargingPoints();
    }

    public StationBuilder withChargingPoints(Map<ChargingPointId, ChargingPoint> chargingPoints) {
        this.chargingPoints = chargingPoints;
        return this;
    }

    public StationBuilder withId(StationId stationId) {
        this.stationId = stationId;
        return this;
    }

    public StationBuilder withLocation(StationLocation stationLocation) {
        this.stationLocation = stationLocation;
        return this;
    }

    public StationBuilder withInMaintenance(boolean inMaintenance) {
        this.inMaintenance = inMaintenance;
        return this;
    }

    public Station build() {
        return new Station(
                stationId,
                stationLocation,
                name,
                maxCapacity,
                inMaintenance,
                chargingPoints
        );
    }

    private Map<ChargingPointId, ChargingPoint> createChargingPoints() {
        Map<ChargingPointId, ChargingPoint> points = new HashMap<>(6);
        for (int i = 1; i <= 6; i++) {
            ChargingPointId id = new ChargingPointId(i);
            ChargingPoint point;
            if (i % 2 == 0) {
                point = new ChargingPoint(id, new Bicycle(new BicycleId(UUID.randomUUID()), 100));
            } else {
                point = new ChargingPoint(id, null);
            }
            points.put(point.getId(), point);
        }
        return points;
    }

}
