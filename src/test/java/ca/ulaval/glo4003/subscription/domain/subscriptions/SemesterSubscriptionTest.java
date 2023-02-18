package ca.ulaval.glo4003.subscription.domain.subscriptions;

import ca.ulaval.glo4003.subscription.domain.Subscription;
import ca.ulaval.glo4003.subscription.domain.SubscriptionBuilder;
import ca.ulaval.glo4003.subscription.domain.TravelTimePlan;
import ca.ulaval.glo4003.subscription.domain.payment.Invoice;
import ca.ulaval.glo4003.subscription.domain.payment.Money;
import ca.ulaval.glo4003.subscription.domain.payment.PaymentClient;
import ca.ulaval.glo4003.subscription.domain.payment.creditCard.CreditCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class SemesterSubscriptionTest {

    public static final TravelTimePlan TRAVEL_TIME_PLAN = TravelTimePlan.TEN_MINUTES;
    public static final long TRIP_DURATION_THAT_EXCEEDS_LIMIT = 1395;
    public static final double EXPECTED_EXTRA_CHARGE = 0.7;
    public static final Money SUBSCRIPTION_PRICE = TRAVEL_TIME_PLAN.getPrice();
    private PaymentClient paymentClient;
    private SemesterSubscription subscription;
    private CreditCard creditCard;

    @BeforeEach
    public void setUp() {
        paymentClient = Mockito.mock(PaymentClient.class);
        subscription =
                new SubscriptionBuilder().withTravelTimePlan(TRAVEL_TIME_PLAN)
                        .withPaymentClient(paymentClient).build();
        creditCard = Mockito.mock(CreditCard.class);
    }

    @Test
    public void givenActiveSemester_whenAskingIfActive_thenReturnsTrue() {
        SemesterSubscription subscription = new SubscriptionBuilder().withActiveSemester().build();

        boolean isActive = subscription.isActive();

        assertTrue(isActive);
    }

    @Test
    public void givenInactiveSemester_whenAskingIfActive_thenReturnsFalse() {
        SemesterSubscription subscription =
                new SubscriptionBuilder().withInactiveSemester().build();

        boolean isActive = subscription.isActive();

        assertFalse(isActive);
    }

    @Test
    public void whenPurchasing_thenDelegatesToPaymentClient() {
        subscription.purchase(creditCard);

        Mockito.verify(paymentClient).debit(SUBSCRIPTION_PRICE, creditCard);
    }

    @Test
    public void whenPurchasing_thenCreatesAnInvoice() {
        Invoice invoice = subscription.purchase(creditCard);

        assertEquals(SUBSCRIPTION_PRICE, invoice.getCost());
    }

    @Test
    public void givenADurationEqualToTravelTimeLimit_whenCalculatingExtraCharge_thenShouldGiveNoExtraCharge() {
        Duration duration = Duration.ofSeconds(TRAVEL_TIME_PLAN.getTravelTimeInSeconds());

        Money money = subscription.calculateExtraCharge(duration);

        assertEquals(0, money.toDouble());
    }

    @Test
    public void givenDurationsExceedingTravelTimeLimit_whenCalculatingExtraCharge_thenShouldGiveTheRightAmountOfMoney() {
        Duration anotherDuration = Duration.ofSeconds(TRIP_DURATION_THAT_EXCEEDS_LIMIT);

        Money extraCharge = subscription.calculateExtraCharge(anotherDuration);

        assertEquals(EXPECTED_EXTRA_CHARGE, extraCharge.toDouble());
    }

    @Test
    public void givenAnotherSubscriptionWithSameSemester_whenAskingIfSemestersMatch_thenReturnsTrue() {
        Subscription anotherSubscription = new SubscriptionBuilder().build();

        boolean matchesSemester = subscription.matchesSemester(anotherSubscription);

        assertTrue(matchesSemester);
    }

    @Test
    public void givenAnotherSubscriptionWithDifferentSemester_whenAskingIfSemestersMatch_thenReturnsFalse() {
        Subscription anotherSubscription = new SubscriptionBuilder().withInactiveSemester().build();

        boolean matchesSemester = subscription.matchesSemester(anotherSubscription);

        assertFalse(matchesSemester);
    }
}
