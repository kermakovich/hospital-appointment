package solvd.laba.ermakovich.ha.domain.exception;

public class PatientNotFoundException extends RuntimeException {
    public static final String MESSAGE = "Patient with  id: %d doesn`t exist";

    public PatientNotFoundException(long id) {
        super(String.format(MESSAGE, id));
    }
}
