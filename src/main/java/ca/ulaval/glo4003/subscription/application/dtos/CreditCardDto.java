package ca.ulaval.glo4003.subscription.application.dtos;

import ca.ulaval.glo4003.main.application.message.ErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreditCardDto(
        @NotBlank(message = ErrorMessages.MISSING_OWNER_NAME)
        String ownerName,

        @Pattern(regexp = "[0-9]{16}", message = ErrorMessages.NUMBER_FORMAT)
        @NotNull(message = ErrorMessages.MISSING_NUMBER)
        String number,

        @Pattern(regexp = "(?:0[1-9]|1[0-2])/[0-9]{2}", message = ErrorMessages.EXPIRATION_DATE_FORMAT)
        @NotNull(message = ErrorMessages.MISSING_EXPIRATION_DATE)
        String expiration,

        @Pattern(regexp = "[0-9]{3}", message = ErrorMessages.SECURITY_CODE_FORMAT)
        @NotNull(message = ErrorMessages.MISSING_SECURITY_CODE)
        String securityCode
) {
}
