package ca.ulaval.glo4003.user.domain;

import java.time.LocalDate;

public class UserBuilder {
    private final String name;
    private final LocalDate birthDate;
    private final Gender gender;
    private UserId userId;
    private EmailAddress emailAddress;
    private UserPassword password;
    private UserRole role;

    public UserBuilder() {
        this.name = "Willy";
        this.birthDate = LocalDate.of(2000, 10, 5);
        this.gender = Gender.MALE;
        this.userId = new UserId("123456789");
        this.emailAddress = new EmailAddress("willy@wonka.ca");
        this.password = new UserPassword("123434");
        this.role = UserRole.DEFAULT;
    }

    public UserBuilder withUserId(UserId userId) {
        this.userId = userId;
        return this;
    }

    public UserBuilder withEmail(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public UserBuilder withPassword(UserPassword password) {
        this.password = password;
        return this;
    }

    public User build() {
        return new User(
                name,
                birthDate,
                gender,
                userId,
                emailAddress,
                password,
                role
        );
    }

    public UserBuilder withRole(UserRole role) {
        this.role = role;
        return this;
    }
}
