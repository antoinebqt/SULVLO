package ca.ulaval.glo4003.user.infrastructure.providers;

import ca.ulaval.glo4003.user.application.dtos.UserCreationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class JsonEmployeeProviderTest {

    private JsonEmployeeProvider jsonEmployeeProvider;

    @BeforeEach
    public void setUp() {
        jsonEmployeeProvider = new JsonEmployeeProvider();
    }

    @Test
    public void whenGettingEmployees_thenReturnsEmployeeDtos() {
        List<UserCreationDto> employeeCreationDtos = jsonEmployeeProvider.getEmployees();

        Assertions.assertFalse(employeeCreationDtos.isEmpty());
    }

}