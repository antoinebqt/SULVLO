package ca.ulaval.glo4003.user.domain;

import ca.ulaval.glo4003.main.domain.hash.HashingTool;
import ca.ulaval.glo4003.user.domain.exceptions.UserNotOldEnoughException;

import java.time.LocalDate;

public class UserFactory {
    private static final int USER_MINIMUM_AGE = 18;

    public User create(String name, LocalDate birthDate, String gender, String userId, String email,
                       String password, UserRole role) {
        this.validateAge(birthDate);
        return new User(
                name,
                birthDate,
                Gender.fromValue(gender),
                new UserId(userId),
                new EmailAddress(email),
                new UserPassword(HashingTool.hashString(password)),
                role
        );
    }

    private void validateAge(LocalDate birthDate) {
        LocalDate majorityBirthday = birthDate.plusYears(USER_MINIMUM_AGE);
        if (majorityBirthday.isAfter(LocalDate.now())) {
            throw new UserNotOldEnoughException();
        }
    }
}
