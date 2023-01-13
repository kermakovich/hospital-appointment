package solvd.laba.ermakovich.ha.service;

import solvd.laba.ermakovich.ha.domain.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {

    List<LocalTime> getTimeSlotsByDoctorIdAndDate(long id, LocalDate date);

    Appointment save(long patientId, Appointment appointment);

    void delete(long appointmentId);

    List<Appointment> getAllByDoctorIdAndDate(long doctorId, LocalDate date);

    List<Appointment> getAllFutureByDoctorId(long doctorId);

    List<Appointment> getAllFutureByPatientId(long patientId);

    boolean isExistPastByPatientIdAndDoctorId(long patientId, long doctorId);

    List<Appointment> getAllByPatientIdAndDate(long patientId, LocalDate date);

}
