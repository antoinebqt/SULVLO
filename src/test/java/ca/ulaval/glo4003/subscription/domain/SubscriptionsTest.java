package ca.ulaval.glo4003.subscription.domain;

import ca.ulaval.glo4003.subscription.application.exceptions.SubscriptionAlreadyExistsException;
import ca.ulaval.glo4003.subscription.domain.exceptions.SubscriptionNotFoundException;
import ca.ulaval.glo4003.subscription.domain.payment.Invoice;
import ca.ulaval.glo4003.subscription.domain.payment.Money;
import ca.ulaval.glo4003.subscription.domain.payment.creditCard.CreditCard;
import ca.ulaval.glo4003.subscription.domain.payment.creditCard.CreditCardBuilder;
import ca.ulaval.glo4003.trip.domain.Trip;
import ca.ulaval.glo4003.trip.domain.TripBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDateTime;
import java.util.List;

class SubscriptionsTest {

    private Subscription subscription;
    private CreditCard creditCard;
    private Subscriptions activeSubscriptions;
    private Subscriptions inactiveSubscriptions;

    @BeforeEach
    public void setUp() {
        subscription = new SubscriptionBuilder().build();
        creditCard = new CreditCardBuilder().build();
        inactiveSubscriptions = new Subscriptions();
        activeSubscriptions = new Subscriptions(List.of(subscription));
    }

    @Test
    public void givenActiveSubscription_whenAskingIfHasActiveSubscription_thenReturnsTrue() {
        boolean hasActiveSubscription = activeSubscriptions.hasActiveSubscription();

        Assertions.assertTrue(hasActiveSubscription);
    }

    @Test
    public void givenNoActiveSubscription_whenAskingIfHasActiveSubscription_thenReturnsFalse() {
        boolean hasActiveSubscription = inactiveSubscriptions.hasActiveSubscription();

        Assertions.assertFalse(hasActiveSubscription);
    }

    @Test
    public void givenSubscription_whenPurchasingSameSubscription_thenPreventsPurchase() {
        Executable purchaseSubscription = () -> activeSubscriptions.purchaseSubscription(subscription, creditCard);

        Assertions.assertThrows(SubscriptionAlreadyExistsException.class, purchaseSubscription);
    }

    @Test
    public void whenPurchasingSubscription_thenAddsTheSubscription() {
        inactiveSubscriptions.purchaseSubscription(subscription, creditCard);

        Assertions.assertTrue(inactiveSubscriptions.hasActiveSubscription());
    }

    @Test
    public void whenPurchasingSubscription_thenReturnsAnInvoice() {
        Invoice invoice = inactiveSubscriptions.purchaseSubscription(subscription, creditCard);

        Assertions.assertEquals(new Money(30), invoice.getCost());
    }

    @Test
    public void givenLongTrip_whenUpdatingBalance_thenUpdatesTheSubscriptionBalance() {
        Trip trip = new TripBuilder().withDepartureDate(LocalDateTime.MIN).withArrivalDate(LocalDateTime.MAX).build();

        activeSubscriptions.updateBalance(trip);

        Assertions.assertNotEquals(new Money(0), activeSubscriptions.getBalance());
    }

    @Test
    public void givenLongTrip_whenUpdatingBalance_thenUpdatesTheTripExtraCharge() {
        Trip trip = new TripBuilder().withDepartureDate(LocalDateTime.MIN).withArrivalDate(LocalDateTime.MAX).build();

        activeSubscriptions.updateBalance(trip);

        Assertions.assertEquals(trip.getExtraCharge(), activeSubscriptions.getBalance());
    }

    @Test
    public void givenNoActiveSubscription_whenUpdatingBalance_thenPreventsUpdate() {
        Trip trip = new TripBuilder().withDepartureDate(LocalDateTime.MIN).withArrivalDate(LocalDateTime.MAX).build();

        Executable updateBalance = () -> inactiveSubscriptions.updateBalance(trip);

        Assertions.assertThrows(SubscriptionNotFoundException.class, updateBalance);
    }

}
