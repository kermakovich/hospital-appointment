package solvd.laba.ermakovich.ha.repository.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import solvd.laba.ermakovich.ha.domain.Review;
import solvd.laba.ermakovich.ha.repository.ReviewRepository;
import solvd.laba.ermakovich.ha.repository.jdbc.mapper.ReviewMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

//@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private static final String SAVE = "INSERT INTO reviews (id_doctor, id_patient, description) VALUES (?,?,?)";
    private static final String GET_ID_BY_DOCTOR_ID_AND_PATIENT_ID = "SELECT id FROM reviews WHERE id_doctor=? and id_patient=? limit(1)";
    private static final String CHECK_IF_EXISTS_BY_ID = "SELECT id FROM reviews WHERE id = ?";
    private static final String DELETE = "DELETE FROM reviews WHERE id=?";
    private static final String UPDATE = "UPDATE reviews SET description = ? WHERE id = ?";
    private static final String GET_INFO_BY_DOCTOR_ID = """
            SELECT reviews.id as "review_id", reviews.description as "review_description",
            reviews.id_patient as "patient_id", pat.name as "patient_name", reviews.id_doctor as "doctor_id"
            from reviews
            left join user_info pat on pat.id = reviews.id_patient
            WHERE reviews.id_doctor = ?
            """;
    private static final String GET_INFO_BY_ID = """
            SELECT reviews.id as "review_id", reviews.description as "review_description",
            reviews.id_patient as "patient_id", pat.name as "patient_name", reviews.id_doctor as "doctor_id",
            d.id as "doctor_id", d.name as "doctor_name", d.surname as "doctor_surname",
            d.fatherhood as "doctor_fatherhood", d.birthday as "doctor_birthday",
            d.email as "doctor_email", doctors.department as "doctor_department",
            doctors.specialization as "doctor_specialization", doctors.cabinet as "doctor_cabinet"
            from reviews
            join user_info pat on pat.id = reviews.id_patient
            join user_info d on d.id = reviews.id_doctor
            left join doctors on doctors.user_id = reviews.id_doctor
            WHERE reviews.id = ?
            """;
    private final DataSourceConfig dataSource;

    @Override
    @SneakyThrows
    public void save(Review review) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, review.getDoctor().getId());
            ps.setLong(2, review.getPatient().getId());ps.setString(3, review.getDescription());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    review.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    @SneakyThrows
    public boolean isExistByDoctorIdAndPatientId(long doctorId, long patientId) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(GET_ID_BY_DOCTOR_ID_AND_PATIENT_ID)) {
            ps.setLong(1, doctorId);
            ps.setLong(2, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    @SneakyThrows
    public boolean isExistById(long reviewId) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(CHECK_IF_EXISTS_BY_ID)) {
            ps.setLong(1, reviewId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }


    @Override
    @SneakyThrows
    public void delete(long reviewId) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(DELETE)) {
            ps.setLong(1, reviewId);
            ps.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public void update(Review review) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(UPDATE)) {
            ps.setString(1, review.getDescription());
            ps.setLong(2, review.getId());
            ps.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public Optional<Review> findById(long id) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(GET_INFO_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(ReviewMapper.map(rs));
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    @SneakyThrows
    public List<Review> findAllByDoctorId(long doctorId) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(GET_INFO_BY_DOCTOR_ID)) {
            ps.setLong(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                return ReviewMapper.mapListForDoctor(rs);
            }
        }
    }

}
