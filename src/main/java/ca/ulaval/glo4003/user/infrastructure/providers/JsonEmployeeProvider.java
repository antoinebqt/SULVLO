package ca.ulaval.glo4003.user.infrastructure.providers;

import ca.ulaval.glo4003.user.application.dtos.UserCreationDto;
import ca.ulaval.glo4003.user.domain.dtos.EmployeeCreationDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

public class JsonEmployeeProvider {
    private static final String EMPLOYEES_CONFIG_FILE_PATH =
            "src/main/java/ca/ulaval/glo4003/user/infrastructure/config/employees.json";

    public List<UserCreationDto> getEmployees() {
        List<EmployeeCreationDto> employeeCreationDtos;
        try {
            ObjectMapper mapper = new ObjectMapper();
            employeeCreationDtos = mapper.readValue(new File(EMPLOYEES_CONFIG_FILE_PATH), new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return employeeCreationDtos.stream().map(e -> new UserCreationDto(e.name(), e.email(), e.userId(), e.password(),
                e.gender(), e.birthDate())).toList();
    }
}
