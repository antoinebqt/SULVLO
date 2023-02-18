package ca.ulaval.glo4003.subscription.domain.subscriptions;

import ca.ulaval.glo4003.subscription.domain.Subscription;
import ca.ulaval.glo4003.subscription.domain.TravelTimePlan;

public class EmployeeSubscription extends Subscription {

    public EmployeeSubscription() {
        super(null, TravelTimePlan.UNLIMITED, null);
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
