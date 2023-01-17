package solvd.laba.ermakovich.ha.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.domain.SearchAppointmentCriteria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Mapper
public interface AppointmentRepository {

    void save(@Param("patientId") long patientId, @Param("appointment") Appointment appointment);

    boolean isExistByDoctorIdAndTime(Appointment appointment);

    boolean isExistById(long appointmentId);

    boolean isExistPastByPatientIdAndDoctorId(@Param("patientId") long patientId, @Param("doctorId") long doctorId);

    boolean isExistByPatientIdAndTime(@Param("patientId") long patientId,@Param("appointment") Appointment appointment);

    List<LocalTime> findAppointmentsTimeByDoctorIdAndDate(@Param("id") long id, @Param("date") LocalDate date);

    List<Appointment> findAllByPatientIdAndCriteria(@Param("patientId") long patientId, @Param("criteria") SearchAppointmentCriteria criteria);

    List<Appointment> findAllByDoctorIdAndCriteria(@Param("doctorId") long doctorId, @Param("criteria") SearchAppointmentCriteria criteria);

    void delete(long appointmentId);

}
