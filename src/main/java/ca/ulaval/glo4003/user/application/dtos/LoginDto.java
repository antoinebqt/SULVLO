package ca.ulaval.glo4003.user.application.dtos;

import ca.ulaval.glo4003.main.application.message.ErrorMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginDto(

        @Email(message = ErrorMessages.EMAIL_FORMAT)
        @NotNull(message = ErrorMessages.MISSING_EMAIL)
        String email,

        @NotBlank(message = ErrorMessages.MISSING_PASSWORD)
        String password
) {
}
