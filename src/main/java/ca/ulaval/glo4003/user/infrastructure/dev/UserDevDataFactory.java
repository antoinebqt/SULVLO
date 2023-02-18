package ca.ulaval.glo4003.user.infrastructure.dev;

import ca.ulaval.glo4003.main.domain.hash.HashingTool;
import ca.ulaval.glo4003.user.domain.*;
import ca.ulaval.glo4003.user.domain.authentication.token.TokenGenerator;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class UserDevDataFactory {

    private static UserDevDataFactory instance;
    private final int tokenDurationInMinutes = 120;
    private final String userEmail = "test@gmail.com";
    private final UserId userId = new UserId("111111111");
    private final UserId technicianUserId = new UserId("123123123");
    private final String passwordValue = "pwdtest";
    private final TokenGenerator tokenGenerator;
    private final User user;
    private final User technician;

    public UserDevDataFactory(TokenGenerator tokenGenerator) {
        UserPassword userPassword = new UserPassword(HashingTool.hashString(passwordValue));
        user = new User("bob", LocalDate.now(), Gender.MALE, userId, new EmailAddress(userEmail),
                userPassword, UserRole.DEFAULT);
        String technicianEmail = "test@sulvlo.com";
        technician = new User("bobe", LocalDate.now(), Gender.FEMALE, technicianUserId,
                new EmailAddress(technicianEmail), userPassword,
                UserRole.TECHNICIAN);
        this.tokenGenerator = tokenGenerator;
    }

    public static UserDevDataFactory getInstance() {
        return instance;
    }

    public static void setInstance(UserDevDataFactory userDevDataFactory) {
        instance = userDevDataFactory;
    }

    public User createUser() {
        return user;
    }

    public String getBearerToken() {
        Instant tokenEndTimestamp = Instant.now().plus(tokenDurationInMinutes, ChronoUnit.MINUTES);
        return tokenGenerator.generate(user, tokenEndTimestamp).toString();
    }

    public String getTechnicianBearerToken() {
        Instant tokenEndTimestamp = Instant.now().plus(tokenDurationInMinutes, ChronoUnit.MINUTES);
        return tokenGenerator.generate(technician, tokenEndTimestamp).toString();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return passwordValue;
    }

    public UserId getUserId() {
        return userId;
    }

    public UserId getTechnicianUserId() {
        return technicianUserId;
    }
}
