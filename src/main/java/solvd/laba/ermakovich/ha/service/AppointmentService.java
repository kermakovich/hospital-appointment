package solvd.laba.ermakovich.ha.service;

import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.domain.SearchAppointmentCriteria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {

    List<LocalTime> getTimeSlotsByDoctorIdAndDate(long id, LocalDate date);

    Appointment create(long patientId, Appointment appointment);

    void delete(long appointmentId);

    boolean isExistPastByPatientIdAndDoctorId(long patientId, long doctorId);

    List<Appointment> retrieveAllByPatientIdAndCriteria(long patientId, SearchAppointmentCriteria criteria);

    List<Appointment> retrieveAllByDoctorIdAndCriteria(long doctorId, SearchAppointmentCriteria criteria);

    Appointment retrieveById(Long appId);
}
