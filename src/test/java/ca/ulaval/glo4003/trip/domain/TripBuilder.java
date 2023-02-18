package ca.ulaval.glo4003.trip.domain;

import ca.ulaval.glo4003.station.domain.StationLocation;
import ca.ulaval.glo4003.trip.domain.bicycle.Bicycle;
import ca.ulaval.glo4003.trip.domain.bicycle.BicycleId;

import java.time.LocalDateTime;

public class TripBuilder {

    private StationLocation from;
    private StationLocation to;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private Bicycle bicycle;

    public TripBuilder() {
        this.from = StationLocation.CASAULT;
        this.to = null;
        this.departureDate = LocalDateTime.now();
        this.arrivalDate = null;
        this.bicycle = new Bicycle(new BicycleId(), 100);
    }

    public TripBuilder withDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public TripBuilder withDepartureStation(StationLocation stationLocation) {
        this.from = stationLocation;
        return this;
    }

    public TripBuilder withArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
        return this;
    }

    public TripBuilder withArrivalStation(StationLocation arrivalStation) {
        this.to = arrivalStation;
        return this;
    }

    public TripBuilder withBicycle(Bicycle bicycle) {
        this.bicycle = bicycle;
        return this;
    }

    public TripBuilder withBicycleInUse() {
        this.bicycle.activate();
        return this;
    }

    public Trip build() {
        return new Trip(
                from,
                to,
                departureDate,
                arrivalDate,
                bicycle
        );
    }
}
