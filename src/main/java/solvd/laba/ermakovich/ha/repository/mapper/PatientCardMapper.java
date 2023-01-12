package solvd.laba.ermakovich.ha.repository.mapper;

import lombok.SneakyThrows;
import solvd.laba.ermakovich.ha.domain.PatientCard;

import java.sql.ResultSet;
import java.util.UUID;

public abstract class PatientCardMapper {

    @SneakyThrows
    public static PatientCard map(ResultSet rs) {
        PatientCard patientCard = new PatientCard();
        patientCard.setId(rs.getLong("patient_card_id"));
        patientCard.setNumber(UUID.fromString(rs.getString("card_number")));
        patientCard.setRegistryDate(rs.getDate("reg_date_card").toLocalDate());
        return patientCard;
    }

}
