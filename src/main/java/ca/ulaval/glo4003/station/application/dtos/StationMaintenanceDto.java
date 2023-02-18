package ca.ulaval.glo4003.station.application.dtos;

import ca.ulaval.glo4003.main.application.message.ErrorMessages;
import jakarta.validation.constraints.NotNull;

public record StationMaintenanceDto(
        @NotNull(message = ErrorMessages.MISSING_IN_MAINTENANCE) boolean inMaintenance) {
}
