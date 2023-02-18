package ca.ulaval.glo4003.trip.domain.traveler;

import ca.ulaval.glo4003.code.application.exceptions.UnauthorizedTripException;
import ca.ulaval.glo4003.code.domain.UnlockCode;
import ca.ulaval.glo4003.station.domain.Station;
import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPointId;
import ca.ulaval.glo4003.subscription.domain.Subscription;
import ca.ulaval.glo4003.subscription.domain.Subscriptions;
import ca.ulaval.glo4003.subscription.domain.payment.Invoice;
import ca.ulaval.glo4003.subscription.domain.payment.Money;
import ca.ulaval.glo4003.subscription.domain.payment.creditCard.CreditCard;
import ca.ulaval.glo4003.trip.domain.Trip;
import ca.ulaval.glo4003.trip.domain.Trips;
import ca.ulaval.glo4003.user.domain.EmailAddress;
import ca.ulaval.glo4003.user.domain.UserId;

import java.util.List;

public class Traveler {

    private final UserId id;
    private final EmailAddress emailAddress;
    private final Subscriptions subscriptions;
    private final Trips trips;
    private CreditCard creditCard;
    private UnlockCode unlockCode;

    public Traveler(UserId id, EmailAddress emailAddress) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.subscriptions = new Subscriptions();
        this.trips = new Trips();
    }

    public Traveler(UserId id, EmailAddress emailAddress, Subscriptions subscriptions) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.subscriptions = subscriptions;
        this.trips = new Trips();
    }

    public Traveler(UserId userId, EmailAddress emailAddress, Subscriptions subscriptions,
                    Trips trips, UnlockCode unlockCode) {
        this.id = userId;
        this.emailAddress = emailAddress;
        this.subscriptions = subscriptions;
        this.trips = trips;
        this.unlockCode = unlockCode;
    }

    public Invoice purchaseSubscription(Subscription subscription, CreditCard creditCard) {
        if (this.creditCard == null) {
            this.creditCard = creditCard;
        }
        return subscriptions.purchaseSubscription(subscription, creditCard);
    }

    public void startTrip(String code, Station station, ChargingPointId chargingPointId) {
        if (this.unlockCode == null || !this.unlockCode.matchesValue(code) || this.unlockCode.isExpired()) {
            throw new UnauthorizedTripException();
        }
        trips.startTrip(station, chargingPointId);
    }

    public void terminateTrip(Station station, ChargingPointId chargingPointId) {
        trips.terminateOngoingTrip(station, chargingPointId, subscriptions);
    }

    public boolean isTravelling() {
        return trips.hasTripInProgress();
    }

    public boolean hasActiveSubscription() {
        return subscriptions.hasActiveSubscription();
    }

    public UserId getId() {
        return id;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions.getAll();
    }

    public List<Trip> getLastMonthTrips() {
        return trips.getLastMonthTrips();
    }

    public UnlockCode getUnlockCode() {
        return unlockCode;
    }

    public void setUnlockCode(UnlockCode unlockCode) {
        if (!subscriptions.hasActiveSubscription()) {
            throw new UnauthorizedTripException();
        }
        this.unlockCode = unlockCode;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public Money getBalance() {
        return subscriptions.getBalance();
    }
}
