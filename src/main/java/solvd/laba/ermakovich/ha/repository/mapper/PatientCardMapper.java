package solvd.laba.ermakovich.ha.repository.mapper;

import solvd.laba.ermakovich.ha.domain.Patient;
import solvd.laba.ermakovich.ha.domain.card.PatientCard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public abstract class PatientCardMapper {
    public static PatientCard map(ResultSet rs) {
        try {
            PatientCard patientCard = new PatientCard();
            Patient patient = PatientMapper.map(rs);
            patientCard.setPatient(patient);
            patientCard.setNumber(UUID.fromString(rs.getString("card_number")));
            patientCard.setRegistryDate(rs.getDate("reg_date_card").toLocalDate());
            return patientCard;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
