package solvd.laba.ermakovich.ha.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.repository.AppointmentRepository;
import solvd.laba.ermakovich.ha.repository.mapper.AppointmentMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class AppointmentRepositoryImpl implements AppointmentRepository {
    private static final String GET_TIME_SLOTS_BY_DOCTOR_AND_DATE = "SELECT date_time_start::time FROM appointments WHERE id_doctor=? and date_time_start::date=?";
    private static final String SAVE = "INSERT INTO appointments (id_doctor, id_card, date_time_start) VALUES (?,?,?)";
    private static final String CHECK_IF_EXISTS_BY_DOCTOR_ID_AND_DATE_TIME = "SELECT id FROM appointments WHERE id_doctor=? and date_time_start=?";

    private static final String GET_INFO = """
            SELECT ap.id, ap.date_time_start, d.id as "doctor_id", d.name as "doctor_name", d.surname as "doctor_surname",\s
            			d.fatherhood as "doctor_fatherhood", d.birthday as "doctor_birthday",\s
                        d.email as "doctor_email", d.password as "doctor_password",
            			doctors.department as "doctor_department", doctors.specialization as "doctor_specialization",
            			cards.number as "card_number", cards.reg_date as "reg_date_card",
            			pat.id as "patient_id",
            			pat.name as "patient_name", pat.surname as "patient_surname",\s
            			pat.fatherhood as "patient_fatherhood", pat.birthday as "patient_birthday",\s
                        pat.email as "patient_email", pat.password as "patient_password",
            			addresses.id as "patient_address_id", addresses.city as "patient_address_city",
            			addresses.street as "patient_address_street", addresses.house as "patient_address_house",
            			addresses.flat as "patient_address_flat"
                        from appointments ap
            			LEFT JOIN doctors on id_doctor = doctors.user_id\s
            			left join patients on id_card = patients.user_id
            			left join patient_cards cards on cards.patient_id = id_card\s
            			join user_info d on  d.id=doctors.user_id
            			join user_info pat on pat.id = patients.user_id\s
            			left join addresses on addresses.id = patients.id_address
            """;
    private static final String WHERE_BY_PATIENT_ID_AND_DOCTOR_ID_AND_BEFORE_NOW = """
            WHERE pat.id=? and d.id=? and ap.date_time_start < now()
            ORDER BY ap.date_time_start
            """;
    private static final String WHERE_BY_PATIENT_ID_AND_TIME_AFTER_NOW = "WHERE pat.id=? and ap.date_time_start > now()";
    private static final String DELETE = "DELETE FROM appointments WHERE id=?";
    private static final String CHECK_IF_EXISTS_BY_ID = "SELECT id FROM appointments WHERE id=?";

    private final DataSource dataSource;
    @Override
    public List<LocalTime> getTimeSlotsByDoctorIdAndDate(long id, LocalDate date) {
        List<LocalTime> timeSlots = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_TIME_SLOTS_BY_DOCTOR_AND_DATE)) {
            ps.setLong(1, id);
            ps.setDate(2, Date.valueOf(date));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    timeSlots.add(rs.getObject(1, LocalTime.class));
                }
                return timeSlots;
            }} catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void save(Appointment appointment) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, appointment.getDoctor().getId());
            ps.setLong(2, appointment.getPatientCard().getPatient().getId());
            ps.setTimestamp(3, Timestamp.valueOf(appointment.getStart()));
            ps.execute();

            try(ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    appointment.setId(rs.getLong(1));
                }
            }} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByDoctorIdAndTime(Appointment appointment) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(CHECK_IF_EXISTS_BY_DOCTOR_ID_AND_DATE_TIME)) {
            ps.setLong(1, appointment.getDoctor().getId());
            ps.setTimestamp(2, Timestamp.valueOf(appointment.getStart()));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Appointment> getAllFutureByPatientId(long patientId) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_INFO + WHERE_BY_PATIENT_ID_AND_TIME_AFTER_NOW)) {
            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                return AppointmentMapper.map(rs);
            }} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long appointmentId) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {
            ps.setLong(1, appointmentId);
    //TODO ask if i need to check
            ps.executeUpdate();
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(long appointmentId) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(CHECK_IF_EXISTS_BY_ID)) {
            ps.setLong(1, appointmentId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Appointment> getAllByPatientIdAndDoctorId(long patientId, long doctorId) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_INFO +
                                                            WHERE_BY_PATIENT_ID_AND_DOCTOR_ID_AND_BEFORE_NOW)) {
            ps.setLong(1, patientId);
            ps.setLong(2, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                return AppointmentMapper.map(rs);
            }} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}