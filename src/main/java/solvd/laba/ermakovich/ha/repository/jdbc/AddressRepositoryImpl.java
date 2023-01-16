package solvd.laba.ermakovich.ha.repository.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import solvd.laba.ermakovich.ha.domain.Address;
import solvd.laba.ermakovich.ha.repository.AddressRepository;
import solvd.laba.ermakovich.ha.repository.DataSourceConfig;
import solvd.laba.ermakovich.ha.repository.jdbc.mapper.AddressMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "persistence", name = "type", havingValue = "jdbc")
public class AddressRepositoryImpl implements AddressRepository {

    private static final String SAVE_ADDRESS = "INSERT INTO addresses (city, street, house, flat) VALUES (?,?,?,?)";
    private static final String GET = """
            SELECT id as "patient_address_id", city as "patient_address_city", street as "patient_address_street",
             house as "patient_address_house", flat as "patient_address_flat"
            FROM addresses
            WHERE city =? and street=? and house=? and flat=?
            """;
    private final DataSourceConfig dataSource;

    @Override
    @SneakyThrows
    public void save(Address address) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(SAVE_ADDRESS, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, address.getCity());
            ps.setString(2, address.getStreet());
            ps.setInt(3, address.getHouse());
            ps.setInt(4, address.getFlat());
            ps.execute();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                long generatedKey = 0L;
                if (rs.next()) {
                    generatedKey = rs.getLong(1);
                }
                address.setId(generatedKey);
            }
        }
    }

    @Override
    @SneakyThrows
    public Optional<Address> find(Address address) {
        Connection con = dataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(GET);
        ps.setString(1, address.getCity());
        ps.setString(2, address.getStreet());
        ps.setInt(3, address.getHouse());
        ps.setInt(4, address.getFlat());
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return Optional.of(AddressMapper.map(rs));
            } else {
                return Optional.empty();
            }
        }
    }

}
