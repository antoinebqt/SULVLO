package ca.ulaval.glo4003.subscription.infrastructure.providers;

import ca.ulaval.glo4003.subscription.domain.semester.Semester;
import ca.ulaval.glo4003.subscription.domain.semester.SemesterProvider;
import ca.ulaval.glo4003.subscription.domain.semester.dtos.SemesterCreationDto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DevSemesterProvider implements SemesterProvider {
    private static final String ACTIVE_START_DATE = "1980-01-01";
    private static final String INACTIVE_START_DATE = "2890-01-01";
    private static final String END_DATE = "2999-12-31";
    private static final String SEMESTER_LABEL = "TEST";
    private static final String INACTIVE_SEMESTER_LABEL = "INACTIVE";

    @Override
    public Map<String, SemesterCreationDto> getSemesters() {
        Map<String, SemesterCreationDto> map = new HashMap<>();

        map.put(SEMESTER_LABEL, createSemesterCreationDto(ACTIVE_START_DATE));
        map.put(INACTIVE_SEMESTER_LABEL, createSemesterCreationDto(INACTIVE_START_DATE));
        return map;
    }

    private SemesterCreationDto createSemesterCreationDto(String startDate) {
        return new SemesterCreationDto(
                Semester.Season.SUMMER.toString(),
                LocalDate.now().getYear(),
                startDate,
                END_DATE
        );

    }

}
