package solvd.laba.ermakovich.ha.domain.exception;

import java.time.LocalDateTime;

public class WrongAppointmentTimeException extends RuntimeException {
    public static final String MESSAGE = "Hospital is close at this time: %s";

    public WrongAppointmentTimeException(LocalDateTime dateTime) {
        super(String.format(MESSAGE, dateTime));
    }
}
