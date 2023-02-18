package ca.ulaval.glo4003.subscription.domain.semester;

import ca.ulaval.glo4003.subscription.domain.exceptions.InvalidSemesterException;
import ca.ulaval.glo4003.subscription.domain.semester.dtos.SemesterCreationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SemesterFactoryTest {

    private static final Clock FIXED_CLOCK =
            Clock.fixed(Instant.now().truncatedTo(ChronoUnit.SECONDS), ZoneId.of("UTC"));
    private SemesterFactory semesterFactory;
    private SemesterProvider semesterProvider;

    @BeforeEach
    public void setUp() {
        semesterProvider = Mockito.mock(SemesterProvider.class);
        semesterFactory = new SemesterFactory(semesterProvider);
    }

    @Test
    public void givenActiveSemester_whenCreatingActiveSemesterLabel_thenReturnsActiveSemesterLabel() {
        givenActiveSemester();

        String semesterLabel = semesterFactory.createActiveSemesterLabel();

        Semester semester = semesterFactory.createAvailableSemester(semesterLabel);
        assertTrue(semester.isActive());
    }

    @Test
    public void givenInvalidSemesterLabel_whenCreatingAvailableSemester_thenPreventsCreation() {
        String invalidSemesterLabel = "xxxxx";

        Executable createAvailableSemester =
                () -> semesterFactory.createAvailableSemester(invalidSemesterLabel);

        assertThrows(InvalidSemesterException.class, createAvailableSemester);
    }

    @Test
    public void givenOutdatedSemesterLabel_whenCreatingAvailableSemester_thenPreventsCreation() {
        String outdatedSemesterLabel = givenOutdatedSemester();

        Executable createAvailableSemester =
                () -> semesterFactory.createAvailableSemester(outdatedSemesterLabel);

        assertThrows(InvalidSemesterException.class, createAvailableSemester);
    }

    @Test
    public void givenSemesterLabel_whenCreatingAvailableSemester_thenReturnsTheAssociatedSemester() {
        String activeSemesterLabel = givenActiveSemester();

        Semester semester = semesterFactory.createAvailableSemester(activeSemesterLabel);

        Map<String, Semester> availableSemesters = semesterFactory.createSemesters();
        Semester expectedSemester = availableSemesters.get(activeSemesterLabel);
        assertEquals(expectedSemester, semester);
    }

    @Test
    public void givenNoActiveSemester_whenCreatingActiveSemester_thenPreventsCreation() {
        givenNoActiveSemester();

        Executable createActiveSemester = () -> semesterFactory.createActiveSemester();

        Assertions.assertThrows(RuntimeException.class, createActiveSemester);
    }

    @Test
    public void givenActiveSemester_whenCreatingActiveSemester_thenReturnsActiveSemester() {
        givenActiveSemester();

        Semester semester = semesterFactory.createActiveSemester();

        assertEquals(Semester.class, semester.getClass());
    }

    private String givenOutdatedSemester() {
        String outdatedSemesterLabel = "outdated";
        Map<String, SemesterCreationDto> semesters = new HashMap<>();
        SemesterCreationDto outdatedSemester =
                new SemesterCreationDto("WINTER", LocalDate.now(FIXED_CLOCK).getYear(),
                        LocalDate.now(FIXED_CLOCK).minusDays(80).toString(),
                        LocalDate.now(FIXED_CLOCK).minusDays(50).toString());
        semesters.put(outdatedSemesterLabel, outdatedSemester);
        Mockito.when(semesterProvider.getSemesters()).thenReturn(semesters);
        return outdatedSemesterLabel;
    }

    private String givenActiveSemester() {
        String activeSemesterLabel = "active";
        Map<String, SemesterCreationDto> semesters = new HashMap<>();
        SemesterCreationDto activeSemester =
                new SemesterCreationDto("WINTER", LocalDate.now(FIXED_CLOCK).getYear(),
                        LocalDate.now(FIXED_CLOCK).minusDays(10).toString(),
                        LocalDate.now(FIXED_CLOCK).plusDays(20).toString());
        semesters.put(activeSemesterLabel, activeSemester);
        Mockito.when(semesterProvider.getSemesters()).thenReturn(semesters);
        return activeSemesterLabel;
    }

    private void givenNoActiveSemester() {
        Mockito.when(semesterProvider.getSemesters()).thenReturn(new HashMap<>());
    }

}
