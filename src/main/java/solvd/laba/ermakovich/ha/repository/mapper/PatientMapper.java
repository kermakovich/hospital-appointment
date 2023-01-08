package solvd.laba.ermakovich.ha.repository.mapper;

import solvd.laba.ermakovich.ha.domain.Address;
import solvd.laba.ermakovich.ha.domain.Patient;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class PatientMapper {
    public static Patient map(ResultSet rs) {
        try {
            Patient patient = new Patient();
            patient.setId(rs.getLong("patient_id"));
            patient.setName(rs.getString("patient_name"));
            patient.setSurname(rs.getString("patient_surname"));
            patient.setFatherhood(rs.getString("patient_fatherhood"));
            patient.setBirthday(rs.getDate("patient_birthday").toLocalDate());
            patient.setEmail(rs.getString("patient_email"));
            patient.setPassword(rs.getString("patient_password"));
            Address address = AddressMapper.map(rs);
            patient.setAddress(address);
            return patient;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
