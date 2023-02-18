package ca.ulaval.glo4003.user.application.assemblers;

import ca.ulaval.glo4003.user.application.dtos.UserDto;
import ca.ulaval.glo4003.user.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserAssembler {

    public List<UserDto> toDtos(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            userDtos.add(new UserDto(
                    user.getId().getValue(),
                    user.getName(),
                    user.getBirthDate().toString(),
                    user.getGender().getValue(),
                    user.getEmailAddress().getValue(),
                    user.getRole().getValue()));
        }
        return userDtos;
    }
}
