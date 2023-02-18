package ca.ulaval.glo4003.user.domain;


import java.time.LocalDate;

public class User {
    private final UserId userId;
    private final String name;
    private final LocalDate birthDate;
    private final Gender gender;
    private final EmailAddress emailAddress;
    private final UserPassword password;
    private final UserRole role;

    public User(String name, LocalDate birthDate, Gender gender,
                UserId userId, EmailAddress emailAddress, UserPassword password, UserRole role) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.password = password;
        this.role = role;
    }

    public UserId getId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public UserPassword getPassword() {
        return password;
    }

    public UserRole getRole() {
        return this.role;
    }

    public boolean isTechnician() {
        return this.role == UserRole.TECHNICIAN;
    }

    public boolean isPasswordValid(UserPassword password) {
        return this.password.equals(password);
    }
}
