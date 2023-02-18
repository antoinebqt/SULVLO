package ca.ulaval.glo4003.main.application;

import ca.ulaval.glo4003.main.application.exceptions.PermissionsException;
import ca.ulaval.glo4003.user.domain.UserRole;

import java.util.List;

public class PermissionsService {
    public void validatePermissions(List<UserRole> acceptedRoles, String userRole) {
        if (!acceptedRoles.contains(UserRole.fromValue(userRole))) {
            throw new PermissionsException();
        }
    }
}
