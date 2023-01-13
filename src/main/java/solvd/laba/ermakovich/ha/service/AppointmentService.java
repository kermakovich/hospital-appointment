package solvd.laba.ermakovich.ha.service;

import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.domain.SearchAppointmentCriteria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {

    List<LocalTime> getTimeSlotsByDoctorIdAndDate(long id, LocalDate date);

    Appointment save(long patientId, Appointment appointment);

    void delete(long appointmentId);

    boolean isExistPastByPatientIdAndDoctorId(long patientId, long doctorId);

    List<Appointment> getAllByPatientIdAndCriteria(long patientId, SearchAppointmentCriteria criteria);

    List<Appointment> getAllByDoctorIdAndCriteria(long doctorId, SearchAppointmentCriteria criteria);
}
