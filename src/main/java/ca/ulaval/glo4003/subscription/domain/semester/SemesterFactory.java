package ca.ulaval.glo4003.subscription.domain.semester;

import ca.ulaval.glo4003.main.domain.date.DateFactory;
import ca.ulaval.glo4003.subscription.domain.exceptions.InvalidSemesterException;
import ca.ulaval.glo4003.subscription.domain.semester.dtos.SemesterCreationDto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SemesterFactory {
    private final SemesterProvider semesterProvider;

    public SemesterFactory(SemesterProvider semesterProvider) {
        this.semesterProvider = semesterProvider;
    }

    public Semester createAvailableSemester(String label) {
        Map<String, SemesterCreationDto> semesterDtos = semesterProvider.getSemesters();

        SemesterCreationDto semesterDto = semesterDtos.get(label);
        if (semesterDto == null) {
            throw new InvalidSemesterException();
        }

        Semester semester = convertToSemester(semesterDto);
        if (semester.isOutdated()) {
            throw new InvalidSemesterException();
        }

        return semester;
    }

    public String createActiveSemesterLabel() {
        Map<String, SemesterCreationDto> semesterDtos = semesterProvider.getSemesters();
        Map<String, Semester> semesters = mapToSemesters(semesterDtos);

        for (var entry : semesters.entrySet()) {
            if (entry.getValue().isActive()) {
                return entry.getKey();
            }
        }
        throw new RuntimeException();
    }

    public Semester createActiveSemester() {
        Map<String, SemesterCreationDto> semesterDtos = semesterProvider.getSemesters();
        SemesterCreationDto semesterDto = semesterDtos.get(createActiveSemesterLabel());

        return convertToSemester(semesterDto);
    }

    public Map<String, Semester> createSemesters() {
        return mapToSemesters(semesterProvider.getSemesters());
    }

    private Semester convertToSemester(SemesterCreationDto semesterCreationDto) {
        Semester.Season season = Semester.Season.valueOf(semesterCreationDto.season().toUpperCase());
        int year = semesterCreationDto.year();
        LocalDate startDate = DateFactory.createDate(semesterCreationDto.startDate());
        LocalDate endDate = DateFactory.createDate(semesterCreationDto.endDate());

        return new Semester(season, year, startDate, endDate);
    }

    private Map<String, Semester> mapToSemesters(Map<String, SemesterCreationDto> semesterDtos) {
        Map<String, Semester> semesters = new HashMap<>();
        for (var entry : semesterDtos.entrySet()) {
            semesters.put(entry.getKey(), convertToSemester(entry.getValue()));
        }

        return semesters;
    }
}
