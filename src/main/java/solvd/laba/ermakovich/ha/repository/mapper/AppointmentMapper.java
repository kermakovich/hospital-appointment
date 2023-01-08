package solvd.laba.ermakovich.ha.repository.mapper;

import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.domain.card.PatientCard;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AppointmentMapper {
    public static List<Appointment> map(ResultSet rs) {
        List<Appointment> appointments = new ArrayList<>();
        try {
            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(rs.getLong("id"));
                appointment.setStart(rs.getTimestamp("date_time_start").toLocalDateTime());
                Doctor doctor = DoctorMapper.map(rs);
                appointment.setDoctor(doctor);
                PatientCard patientCard = PatientCardMapper.map(rs);
                appointment.setPatientCard(patientCard);
                appointments.add(appointment);
            }
            return appointments;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
