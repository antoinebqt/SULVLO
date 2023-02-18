package ca.ulaval.glo4003.trip.domain.traveler;

import ca.ulaval.glo4003.code.application.exceptions.UnauthorizedTripException;
import ca.ulaval.glo4003.code.domain.UnlockCode;
import ca.ulaval.glo4003.station.domain.Station;
import ca.ulaval.glo4003.station.domain.StationBuilder;
import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPointId;
import ca.ulaval.glo4003.subscription.domain.Subscription;
import ca.ulaval.glo4003.subscription.domain.SubscriptionBuilder;
import ca.ulaval.glo4003.subscription.domain.TravelTimePlan;
import ca.ulaval.glo4003.subscription.domain.payment.Invoice;
import ca.ulaval.glo4003.subscription.domain.payment.Money;
import ca.ulaval.glo4003.subscription.domain.payment.creditCard.CreditCard;
import ca.ulaval.glo4003.subscription.domain.payment.creditCard.CreditCardBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDateTime;

class TravelerTest {


    public static final ChargingPointId CHARGING_POINT_ID = new ChargingPointId(2);
    public static final UnlockCode UNLOCK_CODE = new UnlockCode("104014", LocalDateTime.now());
    public static final String INVALID_UNLOCK_CODE = "113331";
    public static final ChargingPointId EMPTY_CHARGING_POINT_ID = new ChargingPointId(3);
    public static final TravelTimePlan TRAVEL_TIME_PLAN = TravelTimePlan.TEN_MINUTES;
    private Station station;
    private Traveler traveler;

    @BeforeEach
    public void setUp() {
        station = new StationBuilder().build();
        traveler = new TravelerBuilder().withUnlockCode(UNLOCK_CODE).build();
    }

    @Test
    public void givenNoInProgressTrip_whenStartingTrip_thenTravelerIsNowTraveling() {
        traveler.startTrip(UNLOCK_CODE.getValue(), station, CHARGING_POINT_ID);

        Assertions.assertTrue(traveler.isTravelling());
    }

    @Test
    public void givenInvalidUnlockCode_whenStartingTrip_thenPreventsStart() {
        Executable startTrip = () -> traveler.startTrip(INVALID_UNLOCK_CODE, station, CHARGING_POINT_ID);

        Assertions.assertThrows(UnauthorizedTripException.class, startTrip);
    }

    @Test
    public void givenOnGoingTrip_whenTerminatingTrip_thenTravelerIsNoLongerTraveling() {
        Traveler traveler =
                new TravelerBuilder().withOnGoingTrip().withActiveSubscription().withUnlockCode(UNLOCK_CODE).build();

        traveler.terminateTrip(station, EMPTY_CHARGING_POINT_ID);

        Assertions.assertFalse(traveler.isTravelling());
    }

    @Test
    public void givenOnGoingTrip_whenTerminatingTrip_thenTravelerBalanceIsUpdated() {
        Traveler traveler =
                new TravelerBuilder().withOnGoingTrip().withActiveSubscription().withUnlockCode(UNLOCK_CODE).build();

        traveler.terminateTrip(station, EMPTY_CHARGING_POINT_ID);

        Assertions.assertNotEquals(new Money(0), traveler.getBalance());
    }

    @Test
    public void whenPurchasingSubscription_thenReturnsAnInvoice() {
        Subscription subscription = new SubscriptionBuilder().withTravelTimePlan(TRAVEL_TIME_PLAN).build();
        CreditCard creditCard = new CreditCardBuilder().build();

        Invoice invoice = traveler.purchaseSubscription(subscription, creditCard);

        Assertions.assertEquals(TRAVEL_TIME_PLAN.getPrice(), invoice.getCost());
    }

    @Test
    public void whenPurchasingSubscription_thenActivatesTheSubscription() {
        Subscription subscription = new SubscriptionBuilder().build();
        CreditCard creditCard = new CreditCardBuilder().build();

        traveler.purchaseSubscription(subscription, creditCard);

        Assertions.assertTrue(traveler.hasActiveSubscription());
    }

    @Test
    public void whenPurchasingSubscription_thenSavesTheCreditCardUsed() {
        Subscription subscription = new SubscriptionBuilder().build();
        CreditCard creditCard = new CreditCardBuilder().build();

        traveler.purchaseSubscription(subscription, creditCard);

        Assertions.assertEquals(creditCard, traveler.getCreditCard());
    }

    @Test
    public void givenNoActiveSubscription_whenSettingUnlockCode_thenPreventsIt() {
        Executable setUnlockCode = () -> traveler.setUnlockCode(UNLOCK_CODE);

        Assertions.assertThrows(UnauthorizedTripException.class, setUnlockCode);
    }

}
