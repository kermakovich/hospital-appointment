package solvd.laba.ermakovich.ha.domain.exception;

public class DoctorNotFoundException extends RuntimeException {

    public static final String MESSAGE = "Doctor with id: %d doesn`t exist";

    public DoctorNotFoundException(long id) {
        super(String.format(MESSAGE, id));
    }
}
