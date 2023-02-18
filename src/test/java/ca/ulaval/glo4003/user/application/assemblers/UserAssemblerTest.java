package ca.ulaval.glo4003.user.application.assemblers;

import ca.ulaval.glo4003.user.application.dtos.UserDto;
import ca.ulaval.glo4003.user.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserAssemblerTest {
    private static final String USER_NAME = "Pablo";
    private static final Gender USER_GENDER = Gender.MALE;
    private static final LocalDate USER_BIRTH_DATE = LocalDate.of(1979, 1, 2);
    private static final String USER_ULAVAL_ID = "123456789";
    private static final String USER_EMAIL = "pablo_escobar@mexico.com";
    private static final String USER_PASSWORD = "WeLoveSnow123@";
    private static final UserRole USER_ROLE = UserRole.DEFAULT;

    private static final User A_USER = new User(
            USER_NAME,
            USER_BIRTH_DATE,
            USER_GENDER,
            new UserId(USER_ULAVAL_ID),
            new EmailAddress(USER_EMAIL),
            new UserPassword(USER_PASSWORD),
            USER_ROLE
    );
    private UserAssembler userAssembler;

    @BeforeEach
    void setUp() {
        userAssembler = new UserAssembler();
    }

    @Test
    public void whenConvertingToDtos_thenUserDtoHasSameFields() {
        List<UserDto> usersDto = userAssembler.toDtos(List.of(A_USER));

        UserDto userDto = usersDto.get(0);
        assertEquals(USER_NAME, userDto.name());
        assertEquals(USER_BIRTH_DATE.toString(), userDto.birthDate());
        assertEquals(USER_GENDER.getValue(), userDto.gender());
        assertEquals(USER_ULAVAL_ID, userDto.userId());
        assertEquals(USER_EMAIL, userDto.emailAddress());
        assertEquals(USER_ROLE.getValue(), userDto.role());
    }

    @Test
    public void givenTwoUsers_whenConvertingToDtos_thenReturnsTwoUsersDto() {
        List<User> users = List.of(A_USER, new UserBuilder().build());

        List<UserDto> usersDto = userAssembler.toDtos(users);

        assertEquals(users.size(), usersDto.size());
    }
}
