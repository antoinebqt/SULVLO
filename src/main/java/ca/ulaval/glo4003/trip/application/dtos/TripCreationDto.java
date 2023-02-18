package ca.ulaval.glo4003.trip.application.dtos;

import ca.ulaval.glo4003.main.application.message.ErrorMessages;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TripCreationDto(
        @NotNull(message = ErrorMessages.MISSING_CODE)
        @Digits(integer = 10, fraction = 0, message = ErrorMessages.CODE_FORMAT)
        @Size(min = 5, max = 10, message = ErrorMessages.CODE_LENGTH)
        String code,

        @NotBlank(message = ErrorMessages.MISSING_DEPARTURE_LOCATION)
        String departureLocation,

        @NotNull(message = ErrorMessages.MISSING_CHARGING_POINT_ID)
        Integer chargingPointId
) {
}
