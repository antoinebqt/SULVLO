package ca.ulaval.glo4003.user.application.dtos;

import ca.ulaval.glo4003.main.application.message.ErrorMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserCreationDto(
        @NotBlank(message = ErrorMessages.MISSING_NAME)
        String name,
        @Email(message = ErrorMessages.EMAIL_FORMAT)
        @NotNull(message = ErrorMessages.MISSING_EMAIL)
        String email,
        @NotNull(message = ErrorMessages.MISSING_U_LAVAL_ID)
        @Pattern(regexp = "([0-9]{3} ?){3}", message = ErrorMessages.U_LAVAL_ID_FORMAT)
        String uLavalId,
        @NotBlank(message = ErrorMessages.MISSING_PASSWORD)
        String password,
        @NotBlank(message = ErrorMessages.MISSING_GENDER)
        String gender,
        @NotBlank(message = ErrorMessages.MISSING_BIRTH_DATE)
        String birthDate

) {
}
