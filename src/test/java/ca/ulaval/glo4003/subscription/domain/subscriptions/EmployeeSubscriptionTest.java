package ca.ulaval.glo4003.subscription.domain.subscriptions;

import ca.ulaval.glo4003.subscription.domain.TravelTimePlan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeSubscriptionTest {

    private EmployeeSubscription employeeSubscription;

    @BeforeEach
    public void setUp() {
        employeeSubscription = new EmployeeSubscription();
    }

    @Test
    public void whenAskingIfIsActive_thenReturnsTrue() {
        Assertions.assertTrue(employeeSubscription.isActive());
    }

    @Test
    public void whenInitializing_thenTravelTimePlanIsUnlimited() {
        Assertions.assertEquals(TravelTimePlan.UNLIMITED, employeeSubscription.getTravelTimePlan());
    }

}