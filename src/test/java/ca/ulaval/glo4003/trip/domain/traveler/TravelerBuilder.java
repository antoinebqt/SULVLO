package ca.ulaval.glo4003.trip.domain.traveler;

import ca.ulaval.glo4003.code.domain.UnlockCode;
import ca.ulaval.glo4003.subscription.domain.Subscription;
import ca.ulaval.glo4003.subscription.domain.SubscriptionBuilder;
import ca.ulaval.glo4003.subscription.domain.Subscriptions;
import ca.ulaval.glo4003.subscription.domain.payment.creditCard.CreditCard;
import ca.ulaval.glo4003.subscription.domain.payment.creditCard.CreditCardBuilder;
import ca.ulaval.glo4003.trip.domain.Trip;
import ca.ulaval.glo4003.trip.domain.TripBuilder;
import ca.ulaval.glo4003.trip.domain.Trips;
import ca.ulaval.glo4003.user.domain.EmailAddress;
import ca.ulaval.glo4003.user.domain.UserId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TravelerBuilder {

    private final EmailAddress emailAddress;
    private final CreditCard creditCard;
    private UserId id;
    private Subscriptions subscriptions;
    private Trips trips;
    private UnlockCode unlockCode;

    public TravelerBuilder() {
        this.id = new UserId("111 111 111");
        this.emailAddress = new EmailAddress("etienne@gmail.com");
        this.trips = new Trips();
        this.subscriptions = new Subscriptions();
        this.creditCard = new CreditCardBuilder().build();
        this.unlockCode = new UnlockCode("310401", LocalDateTime.now());
    }

    public TravelerBuilder withTrips(List<Trip> trips) {
        this.trips = new Trips(trips);
        return this;
    }

    public TravelerBuilder withSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = new Subscriptions(subscriptions);
        return this;
    }


    public TravelerBuilder withActiveSubscription() {
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(new SubscriptionBuilder().build());
        this.subscriptions = new Subscriptions(subscriptions);
        return this;
    }

    public TravelerBuilder withUserId(UserId userId) {
        this.id = userId;
        return this;
    }

    public Traveler build() {
        return new Traveler(id, emailAddress, subscriptions, trips, unlockCode);
    }

    public TravelerBuilder withUnlockCode(UnlockCode unlockCode) {
        this.unlockCode = unlockCode;
        return this;
    }

    public TravelerBuilder withOnGoingTrip() {
        List<Trip> trips = new ArrayList<>();
        trips.add(new TripBuilder().withDepartureDate(LocalDateTime.now().minusMinutes(30)).build());
        this.trips = new Trips(trips);
        return this;
    }
}

