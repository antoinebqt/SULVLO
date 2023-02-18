package ca.ulaval.glo4003.station.application.dtos;

import ca.ulaval.glo4003.main.application.message.ErrorMessages;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record BicycleTransferDto(
        @NotEmpty(message = ErrorMessages.MISSING_CHARGING_POINT_IDS)
        List<Integer> chargingPointIds
) {
}
