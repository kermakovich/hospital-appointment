package solvd.laba.ermakovich.ha.repository;

import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.domain.SearchAppointmentCriteria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository {

    List<LocalTime> findTimeSlotsByDoctorIdAndDate(long id, LocalDate date);

    void create(long patientId, Appointment appointment);

    boolean isExistByDoctorIdAndTime(Appointment appointment);

    void delete(long appointmentId);

    boolean isExistById(long appointmentId);

    boolean isExistPastByPatientIdAndDoctorId(long patientId, long doctorId);

    boolean isExistByPatientIdAndTime(long patientId, Appointment appointment);

    List<Appointment> findAllByPatientIdAndCriteria(long patientId, SearchAppointmentCriteria criteria);

    List<Appointment> findAllByDoctorIdAndCriteria(long doctorId, SearchAppointmentCriteria criteria);
}
