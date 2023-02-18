package ca.ulaval.glo4003.trip.domain;

import ca.ulaval.glo4003.station.domain.Station;
import ca.ulaval.glo4003.station.domain.StationLocation;
import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPointId;
import ca.ulaval.glo4003.subscription.domain.Subscription;
import ca.ulaval.glo4003.subscription.domain.payment.Money;
import ca.ulaval.glo4003.trip.domain.bicycle.Bicycle;
import ca.ulaval.glo4003.trip.domain.exceptions.TripAlreadyEndedException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Trip {

    private final StationLocation departureStation;
    private final LocalDateTime departureDate;
    private final Bicycle bicycle;
    private StationLocation arrivalStation;
    private LocalDateTime arrivalDate;
    private Money extraCharge = new Money(0);

    public Trip(StationLocation from, StationLocation to, LocalDateTime startDate,
                LocalDateTime endDate, Bicycle bicycle) {
        this.departureStation = from;
        this.arrivalStation = to;
        this.departureDate = startDate;
        this.arrivalDate = endDate;
        this.bicycle = bicycle;
    }

    public Trip(StationLocation from, Bicycle bicycle) {
        this.departureStation = from;
        this.departureDate = LocalDateTime.now();
        this.bicycle = bicycle;
    }

    public void end(Station station, ChargingPointId chargingPointId) {
        if (isEnded()) {
            throw new TripAlreadyEndedException();
        }
        station.returnBicycle(chargingPointId, bicycle);
        arrivalDate = LocalDateTime.now();
        arrivalStation = station.getLocation();
    }

    public void calculateCost(Subscription subscription) {
        Duration duration = calculateDuration();
        this.extraCharge = subscription.calculateExtraCharge(duration);
    }

    public Duration calculateDuration() {
        return Duration.between(this.departureDate, this.arrivalDate);
    }

    public boolean hasEndedAfter(LocalDate date) {
        if (isInProgress()) {
            return false;
        }
        return arrivalDate.isAfter(date.atStartOfDay());
    }

    public Bicycle getBicycle() {
        return bicycle;
    }

    public Money getExtraCharge() {
        return extraCharge;
    }

    public StationLocation getArrivalStation() {
        return arrivalStation;
    }

    public boolean isInProgress() {
        return arrivalDate == null;
    }

    public StationLocation getDepartureStation() {
        return departureStation;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public boolean isEnded() {
        return !isInProgress();
    }
}
