package ca.ulaval.glo4003.subscription.application.assemblers;

import ca.ulaval.glo4003.subscription.application.dtos.SubscriptionDto;
import ca.ulaval.glo4003.subscription.domain.Subscription;
import ca.ulaval.glo4003.subscription.domain.SubscriptionBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SubscriptionAssemblerTest {
    private final SubscriptionAssembler subscriptionAssembler = new SubscriptionAssembler();

    @Test
    public void whenAssembling_thenMappingIsValid() {
        Subscription subscription = new SubscriptionBuilder().build();
        List<Subscription> subscriptions = List.of(subscription);

        List<SubscriptionDto> subscriptionDtos = subscriptionAssembler.toDtos(subscriptions);

        int expectedSubscriptionDtosSize = 1;
        SubscriptionDto subscriptionDto = subscriptionDtos.get(0);
        assertEquals(expectedSubscriptionDtosSize, subscriptionDtos.size());
        assertEquals(subscription.getBalance().toDouble(), subscriptionDto.balance());
        assertEquals(subscription.getTravelTimePlan().toString(), subscriptionDto.travelTimeLimit());
        assertEquals(subscription.getSemester().getYear(), subscriptionDto.semester().year());
        assertEquals(subscription.getSemester().getSeason().toString(), subscriptionDto.semester().season());
        assertEquals(subscription.getSemester().getStartDate().toString(), subscriptionDto.semester().startDate());
        assertEquals(subscription.getSemester().getEndDate().toString(), subscriptionDto.semester().endDate());
    }

    @Test
    public void givenNullSemester_whenAssembling_thenSemesterDtoIsNull() {
        Subscription subscriptionWithNullSemester = new SubscriptionBuilder().withNullSemester().build();
        List<Subscription> subscriptions = List.of(subscriptionWithNullSemester);

        List<SubscriptionDto> subscriptionDtos = subscriptionAssembler.toDtos(subscriptions);

        assertNull(subscriptionDtos.get(0).semester());
    }


}