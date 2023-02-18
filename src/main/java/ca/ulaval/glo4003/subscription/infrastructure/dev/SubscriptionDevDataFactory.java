package ca.ulaval.glo4003.subscription.infrastructure.dev;

import ca.ulaval.glo4003.subscription.domain.semester.Semester;
import ca.ulaval.glo4003.subscription.domain.semester.SemesterFactory;

import java.util.Map;
import java.util.Optional;

public class SubscriptionDevDataFactory {
    private static SemesterFactory semesterFactory;

    public static void setSemesterFactory(SemesterFactory semesterFactory) {
        SubscriptionDevDataFactory.semesterFactory = semesterFactory;
    }

    public static String createActiveSemesterLabel() {
        return semesterFactory.createActiveSemesterLabel();
    }

    public static String createUnsubscribedSemesterLabel() {
        String activeSemester = createActiveSemesterLabel();
        Map<String, Semester> semesterMap = semesterFactory.createSemesters();

        Optional<String> semester = semesterMap.keySet().stream().filter(s -> !s.equals(activeSemester)).findFirst();

        return semester.orElse("");
    }

}
