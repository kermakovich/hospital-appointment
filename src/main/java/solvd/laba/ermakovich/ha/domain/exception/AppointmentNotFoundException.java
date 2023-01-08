package solvd.laba.ermakovich.ha.domain.exception;

public class AppointmentNotFoundException extends RuntimeException  {

    public static final String MESSAGE = "Appointment with this id: %d doesn`t exist";

    public AppointmentNotFoundException(long id) {
        super(String.format(MESSAGE, id));
    }

}
