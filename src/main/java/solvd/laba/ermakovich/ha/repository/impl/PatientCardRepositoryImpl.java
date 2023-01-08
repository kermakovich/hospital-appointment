package solvd.laba.ermakovich.ha.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import solvd.laba.ermakovich.ha.domain.Patient;
import solvd.laba.ermakovich.ha.domain.card.PatientCard;
import solvd.laba.ermakovich.ha.repository.PatientCardRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PatientCardRepositoryImpl implements PatientCardRepository {

    private static final String SAVE_PATIENT_CARD = "INSERT INTO patient_cards (patient_id, number, reg_date) VALUES (?,?,?)";
    private final DataSource dataSource;
    @Override
    public PatientCard saveByPatientId(long id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SAVE_PATIENT_CARD, Statement.RETURN_GENERATED_KEYS)) {
            PatientCard card = new PatientCard(new Patient(), UUID.randomUUID(), LocalDate.now());
            ps.setLong(1, id);
            ps.setObject(2, card.getNumber());
            ps.setDate(3, Date.valueOf(card.getRegistryDate()));
            ps.execute();

            try(ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    card.getPatient().setId(rs.getLong(1));
                }
                return card;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
