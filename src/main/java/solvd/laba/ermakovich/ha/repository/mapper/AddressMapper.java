package solvd.laba.ermakovich.ha.repository.mapper;

import solvd.laba.ermakovich.ha.domain.Address;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressMapper {

    public static Address map(ResultSet rs) {
        try {
            Address address = new Address();
            address.setId(rs.getLong("patient_address_id"));
            address.setCity(rs.getString("patient_address_city"));
            address.setStreet(rs.getString("patient_address_street"));
            address.setHouse(rs.getInt("patient_address_house"));
            address.setFlat(rs.getInt("patient_address_flat"));
            return address;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
