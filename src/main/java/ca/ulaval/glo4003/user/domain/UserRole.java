package ca.ulaval.glo4003.user.domain;

import ca.ulaval.glo4003.user.domain.exceptions.InvalidUserRoleException;

public enum UserRole {
    DEFAULT(0, "default"),
    TECHNICIAN(1, "technician"),
    ADMIN(2, "admin");

    private final int priority;
    private final String value;

    UserRole(int priority, String value) {
        this.priority = priority;
        this.value = value;
    }

    public static UserRole fromValue(String value) throws InvalidUserRoleException {
        for (UserRole role : UserRole.values()) {
            if (role.getValue().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new InvalidUserRoleException();
    }

    public String getValue() {
        return value;
    }

    public boolean hasRole(UserRole role) {
        return this.priority >= role.priority;
    }


}
