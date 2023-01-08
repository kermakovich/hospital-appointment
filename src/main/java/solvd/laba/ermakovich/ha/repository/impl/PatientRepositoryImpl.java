package solvd.laba.ermakovich.ha.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import solvd.laba.ermakovich.ha.domain.Patient;
import solvd.laba.ermakovich.ha.repository.PatientRepository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
@RequiredArgsConstructor
public class PatientRepositoryImpl implements PatientRepository {
    private static final String SAVE_PATIENT = "INSERT INTO patients (user_id, id_address) VALUES (?,?)";
    private static final String CHECK_IF_EXISTS_BY_ID ="SELECT user_id, id_address FROM patients WHERE user_id = ?";

    private final DataSource dataSource;

    @Override
    public void save(Patient patient) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SAVE_PATIENT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, patient.getId());
            ps.setLong(2, patient.getAddress().getId());
            ps.execute();

        try(ResultSet rs = ps.getGeneratedKeys()){
            long generatedKey = 0L;
            if (rs.next()) {
                generatedKey = rs.getLong(1);
            }
            patient.setId(generatedKey);
        }} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(long id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(CHECK_IF_EXISTS_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }} catch (SQLException e) {
            throw new RuntimeException(e);
        }      }

}
