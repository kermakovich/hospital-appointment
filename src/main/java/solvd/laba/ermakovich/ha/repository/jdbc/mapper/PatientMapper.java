package solvd.laba.ermakovich.ha.repository.jdbc.mapper;

import lombok.SneakyThrows;
import solvd.laba.ermakovich.ha.domain.Patient;

import java.sql.ResultSet;

public abstract class PatientMapper {

    @SneakyThrows
    public static Patient map(ResultSet rs) {
        Patient patient = mapNameAndId(rs);
        patient.setSurname(rs.getString("patient_surname"));
        patient.setFatherhood(rs.getString("patient_fatherhood"));
        patient.setBirthday(rs.getDate("patient_birthday").toLocalDate());
        patient.setEmail(rs.getString("patient_email"));
        return patient;
    }

    @SneakyThrows
    public static Patient mapNameAndId(ResultSet rs) {
        Patient patient = new Patient();
        patient.setId(rs.getLong("patient_id"));
        patient.setName(rs.getString("patient_name"));
        return patient;
    }

}
