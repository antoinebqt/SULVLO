package ca.ulaval.glo4003.subscription.domain.subscriptions;

import ca.ulaval.glo4003.subscription.domain.Subscription;
import ca.ulaval.glo4003.subscription.domain.TravelTimePlan;
import ca.ulaval.glo4003.subscription.domain.payment.PaymentClient;
import ca.ulaval.glo4003.subscription.domain.semester.Semester;

public class SemesterSubscription extends Subscription {

    public SemesterSubscription(Semester semester, TravelTimePlan travelTimePlan, PaymentClient paymentClient) {
        super(semester, travelTimePlan, paymentClient);
    }

    public boolean isActive() {
        return semester.isActive();
    }
}
