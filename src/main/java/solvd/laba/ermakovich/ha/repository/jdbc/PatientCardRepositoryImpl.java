package solvd.laba.ermakovich.ha.repository.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import solvd.laba.ermakovich.ha.domain.PatientCard;
import solvd.laba.ermakovich.ha.repository.DataSourceConfig;
import solvd.laba.ermakovich.ha.repository.PatientCardRepository;

import java.sql.*;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "persistence", name = "type", havingValue = "jdbc")
public class PatientCardRepositoryImpl implements PatientCardRepository {

    private static final String SAVE_PATIENT_CARD = "INSERT INTO patient_cards (patient_id, number, reg_date) VALUES (?,?,?)";
    private final DataSourceConfig dataSource;

    @Override
    @SneakyThrows
    public void save(PatientCard card) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(SAVE_PATIENT_CARD, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, card.getId());
            ps.setObject(2, card.getNumber());
            ps.setDate(3, Date.valueOf(card.getRegistryDate()));
            ps.execute();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    card.getPatient().setId(rs.getLong(1));
                }
            }
        }
    }

}
