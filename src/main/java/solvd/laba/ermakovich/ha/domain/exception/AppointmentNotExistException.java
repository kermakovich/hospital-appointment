package solvd.laba.ermakovich.ha.domain.exception;

public class AppointmentNotExistException extends RuntimeException  {

    public static final String MESSAGE = "Appointment with this id: %d doesn`t exist";

    public AppointmentNotExistException(long id) {
        super(String.format(MESSAGE, id));
    }

}
