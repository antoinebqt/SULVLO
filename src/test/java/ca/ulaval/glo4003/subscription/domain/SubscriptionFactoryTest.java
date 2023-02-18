package ca.ulaval.glo4003.subscription.domain;

import ca.ulaval.glo4003.subscription.domain.payment.PaymentClient;
import ca.ulaval.glo4003.subscription.domain.semester.Semester;
import ca.ulaval.glo4003.subscription.domain.semester.SemesterFactory;
import ca.ulaval.glo4003.subscription.domain.subscriptions.SemesterSubscription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubscriptionFactoryTest {

    public static final String SEMESTER_LABEL = "H2020";
    private static final int TEN_MINUTES = 10;
    private static final int THIRTY_MINUTES = 30;
    private SubscriptionFactory subscriptionFactory;

    @BeforeEach
    public void setUp() {
        SemesterFactory semesterFactory = Mockito.mock(SemesterFactory.class);
        Semester semester = new Semester(Semester.Season.SUMMER, 2000, LocalDate.now(), LocalDate.now());
        Mockito.when(semesterFactory.createAvailableSemester(SEMESTER_LABEL)).thenReturn(semester);

        PaymentClient paymentClient = Mockito.mock(PaymentClient.class);
        subscriptionFactory = new SubscriptionFactory(paymentClient, semesterFactory);
    }

    @Test
    public void whenCreatingTenMinutesTimeLimitSubscription_thenReturnsSubscription() {
        SemesterSubscription semesterSubscription =
                subscriptionFactory.create(TEN_MINUTES, SEMESTER_LABEL);

        assertEquals(TravelTimePlan.TEN_MINUTES, semesterSubscription.getTravelTimePlan());
    }

    @Test
    public void whenCreatingThirtyMinutesTimeLimitSubscription_thenReturnsSubscription() {
        SemesterSubscription semesterSubscription =
                subscriptionFactory.create(THIRTY_MINUTES, SEMESTER_LABEL);

        assertEquals(TravelTimePlan.THIRTY_MINUTES, semesterSubscription.getTravelTimePlan());
    }
}
