package solvd.laba.ermakovich.ha.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Repository;
import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.domain.PatientCard;
import solvd.laba.ermakovich.ha.domain.SearchAppointmentCriteria;
import solvd.laba.ermakovich.ha.repository.AppointmentRepository;
import solvd.laba.ermakovich.ha.repository.DataSourceConfig;
import solvd.laba.ermakovich.ha.repository.mapper.AppointmentMapper;

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
    private static final String GET_INFO_FOR_DOCTOR = """
            SELECT ap.id, ap.date_time_start,
            			pat.id as "patient_id",
            			pat.name as "patient_name", pat.surname as "patient_surname",
            			pat.fatherhood as "patient_fatherhood", pat.birthday as "patient_birthday",
                        pat.email as "patient_email", cards.number as "card_number", 
                        cards.reg_date as "reg_date_card", cards.patient_id as "patient_card_id"
                        from appointments ap
                        left join patient_cards cards on id_card = cards.patient_id
            			left join patients on id_card = patients.user_id
            			join user_info pat on pat.id = patients.user_id
            			WHERE ap.id_doctor=? 
            """;
    private static final String WHERE_DATE_START = "  ap.date_time_start::date ";
    private static final String GET_INFO_FOR_PATIENT = """
            SELECT ap.id, ap.date_time_start, ap.id_card as "patient_id",
                        d.id as "doctor_id", d.name as "doctor_name", d.surname as "doctor_surname",
            			d.fatherhood as "doctor_fatherhood", d.birthday as "doctor_birthday",
                        d.email as "doctor_email", doctors.department as "doctor_department",
                        doctors.specialization as "doctor_specialization", doctors.cabinet as "doctor_cabinet"
                        from appointments ap
            			LEFT JOIN doctors on id_doctor = doctors.user_id
            			join user_info d on  d.id=doctors.user_id
            			WHERE ap.id_card=?
            """;
    private static final String DELETE = "DELETE FROM appointments WHERE id=?";
    private static final String CHECK_IF_EXISTS_BY_ID = "SELECT id FROM appointments WHERE id=?";
    private static final String CHECK_IF_EXISTS_PAST_BY_PATIENT_ID_AND_DOCTOR_ID = "SELECT id FROM appointments WHERE id_card=? and id_doctor=? and date_time_start < now()";
    private static final String CHECK_IF_EXISTS_BY_PATIENT_ID_AND_DATE_TIME = "SELECT id FROM appointments WHERE id_card=? and date_time_start=?";
    private final DataSourceConfig dataSource;

    @Override
    @SneakyThrows
    public List<LocalTime> findTimeSlotsByDoctorIdAndDate(long id, LocalDate date) {
        List<LocalTime> timeSlots = new ArrayList<>();
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(GET_TIME_SLOTS_BY_DOCTOR_AND_DATE)) {
            ps.setLong(1, id);
            ps.setDate(2, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    timeSlots.add(rs.getObject(1, LocalTime.class));
                }
                return timeSlots;
            }
        }
    }

    @Override
    @SneakyThrows
    public void create(long patientId, Appointment appointment) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, appointment.getDoctor().getId());
            ps.setLong(2, patientId);
            ps.setTimestamp(3, Timestamp.valueOf(appointment.getStart()));
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    appointment.setId(rs.getLong(1));
                    PatientCard patientCard = new PatientCard();
                    patientCard.setId(patientId);
                    appointment.setPatientCard(patientCard);
                }
            }
        }
    }

    @Override
    @SneakyThrows
    public boolean isExistByDoctorIdAndTime(Appointment appointment) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(CHECK_IF_EXISTS_BY_DOCTOR_ID_AND_DATE_TIME)) {
            ps.setLong(1, appointment.getDoctor().getId());
            ps.setTimestamp(2, Timestamp.valueOf(appointment.getStart()));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    @SneakyThrows
    public void delete(long appointmentId) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(DELETE)) {
            ps.setLong(1, appointmentId);
            ps.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public boolean isExistById(long appointmentId) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(CHECK_IF_EXISTS_BY_ID)) {
            ps.setLong(1, appointmentId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }



    @Override
    @SneakyThrows
    public boolean isExistPastByPatientIdAndDoctorId(long patientId, long doctorId) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(CHECK_IF_EXISTS_PAST_BY_PATIENT_ID_AND_DOCTOR_ID )) {
            ps.setLong(1, patientId);
            ps.setLong(2, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    @SneakyThrows
    public boolean isExistByPatientIdAndTime(long patientId, Appointment appointment) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(CHECK_IF_EXISTS_BY_PATIENT_ID_AND_DATE_TIME)) {
            ps.setLong(1, patientId);
            ps.setTimestamp(2, Timestamp.valueOf(appointment.getStart()));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    @SneakyThrows
    public List<Appointment> findAllByPatientIdAndCriteria(long patientId, SearchAppointmentCriteria criteria) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(GET_INFO_FOR_PATIENT
                + buildWhereClause(criteria))) {
            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                return AppointmentMapper.mapListForPatient(rs);
            }
        }
    }

    @Override
    @SneakyThrows
    public List<Appointment> findAllByDoctorIdAndCriteria(long doctorId, SearchAppointmentCriteria criteria) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(GET_INFO_FOR_DOCTOR
                + buildWhereClause(criteria))) {
            ps.setLong(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                return AppointmentMapper.mapListForDoctor(rs);
            }
        }
    }


    private String buildWhereClause(SearchAppointmentCriteria criteria) {
        List<String> conditions = new ArrayList<>();
        if (criteria.getStatus() != null) {
            String compareOp = switch (criteria.getStatus()) {
                                    case FUTURE -> CompareOperation.GREATER_OR_EQUAL.value;
                                    case DONE -> CompareOperation.LESS_OR_EQUAL.value;
                                };
            conditions.add(WHERE_DATE_START +  compareOp + "now()");
        }
        if (criteria.getDate() != null) {
            conditions.add(WHERE_DATE_START + CompareOperation.EQUAL.value + "'"
                                + Date.valueOf(criteria.getDate()) + "'");
        }

        if (!conditions.isEmpty()) {
            return " and " + String.join(" and ", conditions);
        } else {
            return Strings.EMPTY;
        }
    }

    @RequiredArgsConstructor
    enum CompareOperation {

        EQUAL("="),
        GREATER_THAN(">"),
        LESS_THAN("<"),
        GREATER_OR_EQUAL(">="),
        LESS_OR_EQUAL("<=");

        private final String value;
    }

}