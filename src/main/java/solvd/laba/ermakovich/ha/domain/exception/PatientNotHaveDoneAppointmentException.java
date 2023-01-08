package solvd.laba.ermakovich.ha.domain.exception;

public class PatientNotHaveDoneAppointmentException extends RuntimeException {
    public static final String MESSAGE = "Patient (id : %d) has not been treated with doctor (id: %d)";

    public PatientNotHaveDoneAppointmentException(long patientId, long doctorId) {
        super(String.format(MESSAGE, patientId, doctorId));
    }
}
