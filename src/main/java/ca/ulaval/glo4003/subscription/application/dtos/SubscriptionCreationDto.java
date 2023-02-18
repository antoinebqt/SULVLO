package ca.ulaval.glo4003.subscription.application.dtos;

import ca.ulaval.glo4003.main.application.message.ErrorMessages;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubscriptionCreationDto(
        @NotNull(message = ErrorMessages.MISSING_TRAVEL_TIME_PLAN)
        Integer travelTimePlan,

        @NotBlank(message = ErrorMessages.MISSING_SEMESTER)
        String semester,

        @Valid
        @NotNull(message = ErrorMessages.MISSING_CREDIT_CARD)
        CreditCardDto creditCard
) {
}
