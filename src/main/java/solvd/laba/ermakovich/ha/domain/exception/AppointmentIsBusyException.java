package solvd.laba.ermakovich.ha.domain.exception;

import solvd.laba.ermakovich.ha.domain.Appointment;

import java.time.format.DateTimeFormatter;

public class AppointmentIsBusyException extends RuntimeException {
    public static final String MESSAGE = "Appointment is taken (date :%s )";

    public AppointmentIsBusyException(Appointment appointment) {
        super(String.format(MESSAGE, appointment.getStart()
                                                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
    }
}
