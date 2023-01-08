package solvd.laba.ermakovich.ha.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import solvd.laba.ermakovich.ha.domain.Review;
import solvd.laba.ermakovich.ha.repository.ReviewRepository;
import solvd.laba.ermakovich.ha.repository.mapper.ReviewMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private static final String SAVE = "INSERT INTO reviews (id_doctor, id_patient, description) VALUES (?,?,?)";
    private static final String GET_ID_BY_DOCTOR_ID_AND_PATIENT_ID = "SELECT id FROM reviews WHERE id_doctor=? and id_patient=?";
    private static final String CHECK_IF_EXISTS_BY_ID = "SELECT id FROM reviews WHERE id = ?";
    private static final String DELETE = "DELETE FROM reviews WHERE id=?";
    private static final String UPDATE = "UPDATE reviews SET description = ? WHERE id = ?";
    private static final String GET_INFO = """
            SELECT reviews.id as "review_id", reviews.description as "review_description", 
            d.id as "doctor_id", d.name as "doctor_name", 
            d.surname as "doctor_surname",
            d.fatherhood as "doctor_fatherhood", d.birthday as "doctor_birthday",
            d.email as "doctor_email", d.password as "doctor_password",
            doctors.department as "doctor_department", doctors.specialization as "doctor_specialization",
            pat.id as "patient_id",
            pat.name as "patient_name", pat.surname as "patient_surname",
            pat.fatherhood as "patient_fatherhood", pat.birthday as "patient_birthday",
            pat.email as "patient_email", pat.password as "patient_password",
            addresses.id as "patient_address_id", addresses.city as "patient_address_city",
            addresses.street as "patient_address_street", addresses.house as "patient_address_house",
            addresses.flat as "patient_address_flat"
            from reviews
            LEFT JOIN doctors on id_doctor = doctors.user_id
            left join patients on id_patient = patients.user_id
            join user_info d on  d.id=doctors.user_id
            join user_info pat on pat.id = patients.user_id
            left join addresses on addresses.id = patients.id_address 
            """;
    private static final String WHERE_ID = "WHERE reviews.id = ?";
    private static final String WHERE_DOCTOR_ID = "WHERE reviews.id_doctor = ?";

    private final DataSource dataSource;

    @Override
    public void save(Review review) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, review.getDoctor().getId());
            ps.setLong(2, review.getPatient().getId());
            ps.setString(3, review.getDescription());
            ps.execute();

            try(ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    review.setId(rs.getLong(1));
                }
            }} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByDoctorIdAndPatientId(long doctorId, long patientId) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ID_BY_DOCTOR_ID_AND_PATIENT_ID)) {
            ps.setLong(1, doctorId);
            ps.setLong(2, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(long reviewId) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(CHECK_IF_EXISTS_BY_ID)) {
            ps.setLong(1, reviewId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(long reviewId) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {
            ps.setLong(1, reviewId);
            //TODO ask if i need to check
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Review review) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {
            ps.setString(1, review.getDescription());
            ps.setLong(2, review.getId());
            //TODO ask if i need to check
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Review> getById(long id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_INFO + WHERE_ID)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(ReviewMapper.map(rs));
                }
                else return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Review> getAllByDoctorId(long doctorId) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_INFO + WHERE_DOCTOR_ID)) {
            ps.setLong(1, doctorId);

            try (ResultSet rs = ps.executeQuery()) {
                return ReviewMapper.mapList(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }    }
}
