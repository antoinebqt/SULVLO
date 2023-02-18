package ca.ulaval.glo4003.subscription.domain;

import ca.ulaval.glo4003.subscription.domain.payment.PaymentClient;
import ca.ulaval.glo4003.subscription.domain.semester.Semester;
import ca.ulaval.glo4003.subscription.domain.semester.SemesterFactory;
import ca.ulaval.glo4003.subscription.domain.subscriptions.SemesterSubscription;

public class SubscriptionFactory {

    private final PaymentClient paymentClient;
    private final SemesterFactory semesterFactory;

    public SubscriptionFactory(PaymentClient paymentClient, SemesterFactory semesterFactory) {
        this.paymentClient = paymentClient;
        this.semesterFactory = semesterFactory;
    }

    public SemesterSubscription create(int travelTimePlanValue, String semester) {
        Semester availableSemester = semesterFactory.createAvailableSemester(semester);
        TravelTimePlan travelTimePlan = TravelTimePlan.fromValue(travelTimePlanValue);

        return new SemesterSubscription(availableSemester, travelTimePlan, paymentClient);
    }

}

