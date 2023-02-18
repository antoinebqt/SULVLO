package ca.ulaval.glo4003.user.domain;

import ca.ulaval.glo4003.user.domain.exceptions.InvalidGenderException;

public enum Gender {
    MALE("male"),
    FEMALE("female"),
    OTHER("other");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public static Gender fromValue(String value) throws InvalidGenderException {
        for (Gender gender : Gender.values()) {
            if (gender.getValue().equalsIgnoreCase(value)) {
                return gender;
            }
        }
        throw new InvalidGenderException();
    }

    public String getValue() {
        return value;
    }
}
