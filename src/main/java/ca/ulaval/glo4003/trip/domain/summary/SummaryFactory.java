package ca.ulaval.glo4003.trip.domain.summary;

import ca.ulaval.glo4003.station.domain.StationLocation;
import ca.ulaval.glo4003.trip.domain.Trip;

import java.time.Duration;
import java.util.*;

public class SummaryFactory {

    public static final int DEFAULT_COUNTER_VALUE = 0;
    public static final int DEPARTURE_STATION_COUNTER_WEIGHT = 1;
    public static final int ARRIVAL_STATION_COUNTER_WEIGHT = 1;

    public Summary createSummary(List<Trip> trips) {
        if (trips.isEmpty()) return null;

        int totalTravel = trips.size();

        Duration totalTravelTime = Duration.ZERO;
        HashMap<StationLocation, Integer> stationCounter = new HashMap<>();
        for (Trip trip : trips) {
            totalTravelTime = totalTravelTime.plus(trip.calculateDuration());
            stationCounter.put(trip.getDepartureStation(), stationCounter
                    .getOrDefault(trip.getDepartureStation(), DEFAULT_COUNTER_VALUE) +
                    DEPARTURE_STATION_COUNTER_WEIGHT);
            stationCounter.put(trip.getArrivalStation(), stationCounter
                    .getOrDefault(trip.getArrivalStation(), DEFAULT_COUNTER_VALUE) + ARRIVAL_STATION_COUNTER_WEIGHT);
        }

        Duration averageTravelTime = totalTravelTime.dividedBy(totalTravel);
        StationLocation station = Collections.max(stationCounter.entrySet(),
                Comparator.comparingInt(Map.Entry::getValue)).getKey();

        return new Summary(totalTravelTime, averageTravelTime, totalTravel, station);
    }
}
