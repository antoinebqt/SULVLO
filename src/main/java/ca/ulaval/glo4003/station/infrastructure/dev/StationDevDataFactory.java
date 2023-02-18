package ca.ulaval.glo4003.station.infrastructure.dev;

import ca.ulaval.glo4003.station.domain.Station;
import ca.ulaval.glo4003.station.domain.StationId;
import ca.ulaval.glo4003.station.domain.StationLocation;
import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPoint;
import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPointId;
import ca.ulaval.glo4003.trip.domain.bicycle.Bicycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StationDevDataFactory {

    private static final int NUMBER_OF_CHARGING_POINTS = 12;
    private static final StationLocation STATION_LOCATION = StationLocation.CASAULT;
    private static final String STATION_ID = "30303";
    private static final String IN_MAINTENANCE_STATION_ID = "10334";

    public static List<Station> createStations() {
        List<Station> stations = new ArrayList<>();
        Map<ChargingPointId, ChargingPoint> chargingPoints = new HashMap<>(NUMBER_OF_CHARGING_POINTS);

        for (int i = 1; i <= NUMBER_OF_CHARGING_POINTS; i++) {
            ChargingPointId id = new ChargingPointId(i);
            ChargingPoint point;
            if (i % 2 == 0) {
                point = new ChargingPoint(id, new Bicycle());
            } else {
                point = new ChargingPoint(id, null);
            }
            chargingPoints.put(point.getId(), point);
        }

        stations.add(new Station(new StationId(STATION_ID), STATION_LOCATION, "test", 6, false,
                chargingPoints));
        stations.add(
                new Station(new StationId(IN_MAINTENANCE_STATION_ID), StationLocation.PEPS, "test_in_maintenance", 6,
                        true,
                        chargingPoints));

        return stations;
    }

    public static String getStationId() {
        return STATION_ID;
    }

    public static String getInMaintenanceStationId() {
        return IN_MAINTENANCE_STATION_ID;
    }

    public static String getStationLocation() {
        return STATION_LOCATION.getValue();
    }

}
