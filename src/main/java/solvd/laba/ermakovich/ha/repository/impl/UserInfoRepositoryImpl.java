package solvd.laba.ermakovich.ha.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.repository.UserRepository;
import solvd.laba.ermakovich.ha.repository.config.DataSourceConfig;
import solvd.laba.ermakovich.ha.repository.mapper.UsersInfoMapper;

import java.sql.*;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserInfoRepositoryImpl implements UserRepository {

    private static final String SAVE_USER_INFO = "INSERT INTO user_info (name, surname, fatherhood, birthday, email, password) VALUES (?,?,?,?,?,?)";
    private static final String GET_BY_EMAIL = "SELECT id, name, surname, fatherhood, birthday, email, password FROM user_info WHERE email=?";
    private final DataSourceConfig dataSource;

    @Override
    @SneakyThrows
    public void save(UserInfo userInfo) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(SAVE_USER_INFO, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, userInfo.getName());
            ps.setString(2, userInfo.getSurname());
            ps.setString(3, userInfo.getFatherhood());
            ps.setDate(4, Date.valueOf(userInfo.getBirthday()));
            ps.setString(5, userInfo.getEmail());
            ps.setString(6, userInfo.getPassword());
            ps.execute();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    userInfo.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    @SneakyThrows
    public Optional<UserInfo> findByEmail(String email) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(GET_BY_EMAIL)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(UsersInfoMapper.map(rs));
                } else {
                    return Optional.empty();
                }
            }
        }
    }

}
