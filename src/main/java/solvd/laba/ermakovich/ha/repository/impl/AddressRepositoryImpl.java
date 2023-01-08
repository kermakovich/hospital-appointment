package solvd.laba.ermakovich.ha.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import solvd.laba.ermakovich.ha.domain.Address;
import solvd.laba.ermakovich.ha.repository.AddressRepository;
import solvd.laba.ermakovich.ha.repository.mapper.AddressMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {

    private static final String SAVE_ADDRESS = "INSERT INTO addresses (city, street, house, flat) VALUES (?,?,?,?)";
    private static final String GET = """
    SELECT id as "patient_address_id", city as "patient_address_city", street as "patient_address_street",
     house as "patient_address_house", flat as "patient_address_flat"
    FROM addresses
    WHERE city =? and street=? and house=? and flat=?
    """;
    private final DataSource dataSource;

    @Override
    public void save(Address address) {

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SAVE_ADDRESS, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, address.getCity());
            ps.setString(2, address.getStreet());
            ps.setInt(3, address.getHouse());
            ps.setInt(4, address.getFlat());
            ps.execute();

            try(ResultSet rs = ps.getGeneratedKeys()) {
            long generatedKey = 0L;
            if (rs.next()) {
                generatedKey = rs.getLong(1);
            }
            address.setId(generatedKey);
        }} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Address> find(Address address) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(GET)) {
            ps.setString(1, address.getCity());
            ps.setString(2, address.getStreet());
            ps.setInt(3, address.getHouse());
            ps.setInt(4, address.getFlat());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(AddressMapper.map(rs));
                }
                else return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
