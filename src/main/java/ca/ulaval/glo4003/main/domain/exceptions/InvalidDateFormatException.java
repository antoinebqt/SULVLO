package ca.ulaval.glo4003.main.domain.exceptions;

public class InvalidDateFormatException extends RuntimeException {
    public InvalidDateFormatException(String message) {
        super(message);
    }
}