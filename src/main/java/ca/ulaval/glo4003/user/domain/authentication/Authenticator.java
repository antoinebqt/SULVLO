package ca.ulaval.glo4003.user.domain.authentication;

import ca.ulaval.glo4003.user.domain.User;
import ca.ulaval.glo4003.user.domain.UserPassword;
import ca.ulaval.glo4003.user.domain.authentication.token.Token;
import ca.ulaval.glo4003.user.domain.authentication.token.TokenFactory;
import ca.ulaval.glo4003.user.domain.exceptions.WrongPasswordException;

public class Authenticator {

    private final TokenFactory tokenFactory;

    public Authenticator(TokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }

    public Token authenticate(User user, UserPassword password) {
        if (user.isPasswordValid(password)) {
            return tokenFactory.create(user);
        }

        throw new WrongPasswordException();
    }
}
