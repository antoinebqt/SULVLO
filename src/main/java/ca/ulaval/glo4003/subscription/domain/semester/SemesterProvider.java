package ca.ulaval.glo4003.subscription.domain.semester;

import ca.ulaval.glo4003.subscription.domain.semester.dtos.SemesterCreationDto;

import java.util.Map;

public interface SemesterProvider {

    Map<String, SemesterCreationDto> getSemesters();

}
