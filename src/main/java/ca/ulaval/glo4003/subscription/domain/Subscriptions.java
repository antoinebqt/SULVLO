package ca.ulaval.glo4003.subscription.domain;

import ca.ulaval.glo4003.subscription.application.exceptions.SubscriptionAlreadyExistsException;
import ca.ulaval.glo4003.subscription.domain.exceptions.SubscriptionNotFoundException;
import ca.ulaval.glo4003.subscription.domain.payment.Invoice;
import ca.ulaval.glo4003.subscription.domain.payment.Money;
import ca.ulaval.glo4003.subscription.domain.payment.creditCard.CreditCard;
import ca.ulaval.glo4003.trip.domain.Trip;

import java.util.ArrayList;
import java.util.List;

public class Subscriptions {

    private final List<Subscription> subscriptions;

    public Subscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Subscriptions() {
        this.subscriptions = new ArrayList<>();
    }

    public Subscriptions(Subscription subscription) {
        this.subscriptions = List.of(subscription);
    }

    public Invoice purchaseSubscription(Subscription subscription, CreditCard creditCard) {
        if (contains(subscription)) {
            throw new SubscriptionAlreadyExistsException();
        }
        subscriptions.add(subscription);
        return subscription.purchase(creditCard);
    }

    public boolean hasActiveSubscription() {
        return subscriptions.stream().anyMatch(Subscription::isActive);
    }

    public void updateBalance(Trip trip) {
        Subscription subscription = getActiveSubscription();
        trip.calculateCost(subscription);
    }

    public Money getBalance() {
        Subscription subscription = getActiveSubscription();
        return subscription.getBalance();
    }

    public List<Subscription> getAll() {
        return subscriptions;
    }

    private Subscription getActiveSubscription() {
        return subscriptions.stream().filter(Subscription::isActive).findFirst()
                .orElseThrow(SubscriptionNotFoundException::new);
    }

    private boolean contains(Subscription subscription) {
        return subscriptions.stream().anyMatch(s -> s.matchesSemester(subscription));
    }
}
