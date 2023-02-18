package ca.ulaval.glo4003.trip.application.dtos;

import ca.ulaval.glo4003.main.application.message.ErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TripEndDto(
        @NotBlank(message = ErrorMessages.MISSING_ARRIVAL_LOCATION)
        String arrivalLocation,

        @NotNull(message = ErrorMessages.MISSING_CHARGING_POINT_ID)
        Integer chargingPointId
) {
}
