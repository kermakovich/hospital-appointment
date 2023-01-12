package solvd.laba.ermakovich.ha.repository.mapper;

import lombok.SneakyThrows;
import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.domain.Patient;
import solvd.laba.ermakovich.ha.domain.PatientCard;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class AppointmentMapper {

    @SneakyThrows
    public static List<Appointment> mapListForPatient(ResultSet rs) {
        List<Appointment> appointments = new ArrayList<>();
        while (rs.next()) {
            appointments.add(mapForPatient(rs));
        }
        return appointments;
    }

    @SneakyThrows
    public static Appointment mapForPatient(ResultSet rs) {
        Appointment appointment = map(rs);

        Doctor doctor = DoctorMapper.map(rs);
        appointment.setDoctor(doctor);
        return appointment;
    }

    @SneakyThrows
    public static List<Appointment> mapListForDoctor(ResultSet rs) {
        List<Appointment> appointments = new ArrayList<>();
        while (rs.next()) {
            appointments.add(mapForDoctor(rs));
        }
        return appointments;
    }

    @SneakyThrows
    private static Appointment mapForDoctor(ResultSet rs) {
        Appointment appointment = map(rs);

        PatientCard patientCard = PatientCardMapper.map(rs);
        Patient patient = PatientMapper.map(rs);
        patientCard.setPatient(patient);
        appointment.setPatientCard(patientCard);
        return appointment;
    }

    @SneakyThrows
    private static Appointment map(ResultSet rs) {
        Appointment appointment = new Appointment();
        appointment.setId(rs.getLong("id"));
        appointment.setStart(rs.getTimestamp("date_time_start").toLocalDateTime());
        return appointment;
    }

}
