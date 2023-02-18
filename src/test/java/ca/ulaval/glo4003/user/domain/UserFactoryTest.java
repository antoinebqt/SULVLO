package ca.ulaval.glo4003.user.domain;

import ca.ulaval.glo4003.user.domain.exceptions.UserNotOldEnoughException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserFactoryTest {

    private static final String USER_NAME = "FÉLIX";
    private static final LocalDate BIRTH_DATE = LocalDate.of(2000, 1, 1);
    private static final String GENDER = Gender.MALE.getValue();
    private static final String ID = "111 111 111";
    private static final String EMAIL_ADDRESS = "abc@ulaval.ca";
    private static final String PASSWORD = "1234abcd";
    private static final UserRole ROLE = UserRole.DEFAULT;
    private static final Clock FIXED_CLOCK =
            Clock.fixed(Instant.now().truncatedTo(ChronoUnit.SECONDS), ZoneId.of("UTC"));
    private final UserFactory userFactory = new UserFactory();
    private final String A_DATE = "1999-08-10";
    private final String A_TECHNICIAN_ROLE = "technician";

    @Test
    public void givenValidUserInformation_whenCreatingUser_thenShouldReturnAUser() {
        User user = userFactory.create(USER_NAME, BIRTH_DATE, GENDER, ID, EMAIL_ADDRESS, PASSWORD, ROLE);

        assertEquals(User.class, user.getClass());
    }

    @Test
    public void givenBirthDateLessThan18YearsOld_whenCreatingUser_thenShouldNotCreateUser() {
        LocalDate birthDateLessThan18YearsOld = LocalDate.now(FIXED_CLOCK);

        Executable creation = () -> userFactory.create(USER_NAME, birthDateLessThan18YearsOld,
                GENDER, ID, EMAIL_ADDRESS, PASSWORD, ROLE);

        assertThrows(UserNotOldEnoughException.class, creation);
    }

}