package ca.ulaval.glo4003.subscription.infrastructure.providers;

import ca.ulaval.glo4003.subscription.domain.semester.dtos.SemesterCreationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

class JsonSemesterProviderTest {


    private JsonSemesterProvider jsonSemesterProvider;

    @BeforeEach
    public void setUp() {
        jsonSemesterProvider = new JsonSemesterProvider();
    }

    @Test
    public void whenGettingSemesters_thenReturnsSemesters() {
        Map<String, SemesterCreationDto> semesters = jsonSemesterProvider.getSemesters();

        Assertions.assertFalse(semesters.isEmpty());
    }

}
