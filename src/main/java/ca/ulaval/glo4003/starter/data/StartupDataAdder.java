package ca.ulaval.glo4003.starter.data;

import ca.ulaval.glo4003.user.application.UserService;
import ca.ulaval.glo4003.user.application.dtos.UserCreationDto;
import ca.ulaval.glo4003.user.infrastructure.providers.JsonEmployeeProvider;

import java.util.List;

public class StartupDataAdder {

    public void createEmployees(UserService userService) {
        JsonEmployeeProvider employeeParser = new JsonEmployeeProvider();

        List<UserCreationDto> userCreationDtos = employeeParser.getEmployees();

        userCreationDtos.forEach(userService::createTechnician);
    }
}
