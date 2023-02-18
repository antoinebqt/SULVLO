package ca.ulaval.glo4003.subscription.infrastructure.providers;

import ca.ulaval.glo4003.subscription.domain.semester.SemesterProvider;
import ca.ulaval.glo4003.subscription.domain.semester.dtos.SemesterCreationDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Map;

public class JsonSemesterProvider implements SemesterProvider {

    private static final String SEMESTERS_CONFIG_FILE_PATH =
            "src/main/java/ca/ulaval/glo4003/subscription/infrastructure/config/semesters.json";

    @Override
    public Map<String, SemesterCreationDto> getSemesters() {
        Map<String, SemesterCreationDto> semesters;
        try {
            ObjectMapper mapper = new ObjectMapper();
            semesters = mapper.readValue(new File(SEMESTERS_CONFIG_FILE_PATH), new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return semesters;
    }
}
