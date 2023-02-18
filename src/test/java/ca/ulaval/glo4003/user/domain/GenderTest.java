package ca.ulaval.glo4003.user.domain;

import ca.ulaval.glo4003.user.domain.exceptions.InvalidGenderException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class GenderTest {
    public static final String INVALID_GENDER = "giraffe";

    @Test
    public void givenInvalidGenderValue_whenInitializingFromValue_thenShouldPreventIt() {
        Executable fromValue = () -> Gender.fromValue(INVALID_GENDER);

        assertThrows(InvalidGenderException.class, fromValue);
    }

    @Test
    public void givenMaleGenderValue_whenInitializingFromValue_thenShouldReturnMaleGender() {
        Gender returnedGender = Gender.fromValue("male");

        assertEquals(Gender.MALE, returnedGender);
    }

    @Test
    public void givenFemaleGenderValue_whenInitializingFromValue_thenShouldReturnFemaleGender() {
        Gender returnedGender = Gender.fromValue("female");

        assertEquals(Gender.FEMALE, returnedGender);
    }

    @Test
    public void givenOtherGenderValue_whenInitializingFromValue_thenShouldReturnOtherGender() {
        Gender returnedGender = Gender.fromValue("other");

        assertEquals(Gender.OTHER, returnedGender);
    }


    @Test
    public void givenGenderWrittenInUpperCase_whenInitializingFromValue_thenShouldReturnAssociatedGender() {
        String validGenderInUpperCase = "OTHER";

        Executable fromValue = () -> Gender.fromValue(validGenderInUpperCase);

        assertDoesNotThrow(fromValue);
    }

}