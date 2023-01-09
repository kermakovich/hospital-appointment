package solvd.laba.ermakovich.ha.repository;

import solvd.laba.ermakovich.ha.domain.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository {

    List<LocalTime> getTimeSlotsByDoctorIdAndDate(long id, LocalDate date);

    void save(Appointment appointment);

    boolean existsByDoctorIdAndTime(Appointment appointment);

    List<Appointment> getAllFutureByPatientId(long patientId);

    void delete(long appointmentId);

    boolean existsById(long appointmentId);

    List<Appointment> getAllByPatientIdAndDoctorId(long patientId, long doctorId);

    List<Appointment> getAllFutureByDoctorId(long doctorId);
}
