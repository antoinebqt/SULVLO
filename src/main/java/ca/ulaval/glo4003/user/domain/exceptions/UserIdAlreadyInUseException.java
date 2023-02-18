package ca.ulaval.glo4003.user.domain.exceptions;

public class UserIdAlreadyInUseException extends RuntimeException {

    public UserIdAlreadyInUseException() {
        super("Id is already in use");
    }
}
