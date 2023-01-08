package solvd.laba.ermakovich.ha.domain.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public static final String MESSAGE = "User with this email: %s already exist";

    public EmailAlreadyExistsException(String email) {
        super(String.format(MESSAGE, email));
    }

}
