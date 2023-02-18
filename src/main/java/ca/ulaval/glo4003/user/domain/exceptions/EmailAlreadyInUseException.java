package ca.ulaval.glo4003.user.domain.exceptions;

public class EmailAlreadyInUseException extends RuntimeException {

    public EmailAlreadyInUseException() {
        super("Email is already in use");
    }
}
