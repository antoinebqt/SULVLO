package ca.ulaval.glo4003.subscription.domain;

import ca.ulaval.glo4003.subscription.domain.payment.Money;
import ca.ulaval.glo4003.subscription.domain.payment.PaymentClient;
import ca.ulaval.glo4003.subscription.domain.semester.Semester;
import ca.ulaval.glo4003.subscription.domain.subscriptions.SemesterSubscription;
import ca.ulaval.glo4003.subscription.infrastructure.payment.DevPaymentClientImpl;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class SubscriptionBuilder {

    public static final Semester.Season SEASON = Semester.Season.SUMMER;
    private static final Clock FIXED_CLOCK =
            Clock.fixed(Instant.now().truncatedTo(ChronoUnit.SECONDS), ZoneId.of("UTC"));
    public static final int YEAR = LocalDate.now(FIXED_CLOCK).getYear();
    private final Money balance;
    private TravelTimePlan travelTimePlan;
    private PaymentClient paymentClient;
    private Semester semester;

    public SubscriptionBuilder() {
        this.semester = new Semester(SEASON, YEAR,
                LocalDate.now(FIXED_CLOCK).minusDays(30),
                LocalDate.now(FIXED_CLOCK).plusDays(30));
        this.travelTimePlan = TravelTimePlan.TEN_MINUTES;
        this.paymentClient = new DevPaymentClientImpl();
        this.balance = new Money(0);
    }

    public SubscriptionBuilder withTravelTimePlan(TravelTimePlan travelTimePlan) {
        this.travelTimePlan = travelTimePlan;
        return this;
    }

    public SemesterSubscription build() {
        return new SemesterSubscription(
                this.semester,
                this.travelTimePlan,
                this.paymentClient
        );
    }

    public SubscriptionBuilder withInactiveSemester() {
        this.semester = new Semester(SEASON, YEAR, LocalDate.now(FIXED_CLOCK).minusDays(60),
                LocalDate.now(FIXED_CLOCK).minusDays(30));
        return this;
    }

    public SubscriptionBuilder withActiveSemester() {
        this.semester = new Semester(SEASON, YEAR,
                LocalDate.now(FIXED_CLOCK).minusDays(30),
                LocalDate.now(FIXED_CLOCK).plusDays(30));
        return this;
    }

    public SubscriptionBuilder withNullSemester() {
        this.semester = null;
        return this;
    }

    public SubscriptionBuilder withPaymentClient(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
        return this;
    }
}
