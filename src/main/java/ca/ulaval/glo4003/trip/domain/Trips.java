package ca.ulaval.glo4003.trip.domain;

import ca.ulaval.glo4003.code.application.exceptions.UnauthorizedTripException;
import ca.ulaval.glo4003.station.domain.Station;
import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPointId;
import ca.ulaval.glo4003.subscription.domain.Subscriptions;
import ca.ulaval.glo4003.trip.domain.bicycle.Bicycle;
import ca.ulaval.glo4003.trip.domain.exceptions.TripNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Trips {

    private final List<Trip> trips;

    public Trips(List<Trip> trips) {
        this.trips = trips;
    }

    public Trips() {
        this.trips = new ArrayList<>();
    }

    public void startTrip(Station station, ChargingPointId chargingPointId) {
        if (hasTripInProgress()) {
            throw new UnauthorizedTripException();
        }
        Bicycle bicycle = station.rentBicycle(chargingPointId);
        Trip trip = new Trip(station.getLocation(), bicycle);
        trips.add(trip);
    }

    public boolean hasTripInProgress() {
        return trips.stream().anyMatch(Trip::isInProgress);
    }

    public List<Trip> getLastMonthTrips() {
        LocalDate now = LocalDate.now();
        LocalDate firstOfTheMonth = LocalDate.of(now.getYear(), now.getMonth(), 1);
        return trips.stream().filter(trip -> trip.hasEndedAfter(firstOfTheMonth)).collect(Collectors.toList());
    }

    public void terminateOngoingTrip(Station station, ChargingPointId chargingPointId, Subscriptions subscriptions) {
        Trip trip = getOngoingTrip();
        trip.end(station, chargingPointId);
        subscriptions.updateBalance(trip);
    }

    private Trip getOngoingTrip() {
        return trips.stream().filter(Trip::isInProgress).findFirst().orElseThrow(TripNotFoundException::new);
    }
}
