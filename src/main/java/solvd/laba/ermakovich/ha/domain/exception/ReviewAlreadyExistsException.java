package solvd.laba.ermakovich.ha.domain.exception;

public class ReviewAlreadyExistsException extends RuntimeException {
    public static final String MESSAGE = "Review with patient (id:%d) and doctor (id:%d) already exist";

    public ReviewAlreadyExistsException(long doctorId, long patientId) {
        super(String.format(MESSAGE, patientId, doctorId));

    }
}
