package solvd.laba.ermakovich.ha.repository;

import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.domain.SearchAppointmentCriteria;

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

    boolean existsByPatientIdAndTime(long patientId, Appointment appointment);

    List<Appointment> getAllByPatientIdAndCriteria(long patientId, SearchAppointmentCriteria criteria);

    List<Appointment> getAllByDoctorIdAndCriteria(long doctorId, SearchAppointmentCriteria criteria);
}
