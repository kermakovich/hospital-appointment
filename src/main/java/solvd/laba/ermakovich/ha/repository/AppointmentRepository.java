package solvd.laba.ermakovich.ha.repository;

import solvd.laba.ermakovich.ha.domain.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository {

    List<LocalTime> getTimeSlotsByDoctorIdAndDate(long id, LocalDate date);

    void save(long patientId, Appointment appointment);

    boolean existsByDoctorIdAndTime(Appointment appointment);

    void delete(long appointmentId);

    boolean existsById(long appointmentId);

    boolean existsPastByPatientIdAndDoctorId(long patientId, long doctorId);

    List<Appointment> getAllByDoctorIdAndDate(long doctorId, LocalDate date);

    List<Appointment> getAllByPatientIdAndDate(long patientId, LocalDate date);

    boolean existsByPatientIdAndTime(long patientId, Appointment appointment);
}
