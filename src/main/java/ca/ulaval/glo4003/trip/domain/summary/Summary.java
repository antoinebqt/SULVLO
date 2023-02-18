package ca.ulaval.glo4003.trip.domain.summary;

import ca.ulaval.glo4003.station.domain.StationLocation;

import java.time.Duration;
import java.util.Objects;

public class Summary {
    private final Duration totalTravelTime;
    private final Duration averageTravelTime;
    private final int numberOfTrips;
    private final StationLocation favouriteStation;

    Summary(Duration totalTravelTime, Duration averageTravelTime, int numberOfTrips, StationLocation favouriteStation) {
        this.totalTravelTime = totalTravelTime;
        this.averageTravelTime = averageTravelTime;
        this.numberOfTrips = numberOfTrips;
        this.favouriteStation = favouriteStation;
    }

    public Duration getTotalTravelTime() {
        return totalTravelTime;
    }

    public Duration getAverageTravelTime() {
        return averageTravelTime;
    }

    public int getNumberOfTrips() {
        return numberOfTrips;
    }

    public StationLocation getFavouriteStation() {
        return favouriteStation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Summary other = (Summary) o;
        return totalTravelTime.equals(other.totalTravelTime) &&
                averageTravelTime.equals(other.averageTravelTime) &&
                numberOfTrips == other.numberOfTrips &&
                favouriteStation.equals(other.favouriteStation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalTravelTime, averageTravelTime, numberOfTrips, favouriteStation);
    }
}

