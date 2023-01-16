package solvd.laba.ermakovich.ha.repository.jdbc.mapper;

import lombok.SneakyThrows;
import solvd.laba.ermakovich.ha.domain.UserInfo;

import java.sql.ResultSet;

public abstract class UsersInfoMapper {

    @SneakyThrows
    public static UserInfo map(ResultSet rs) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(rs.getLong("id"));
        userInfo.setName(rs.getString("name"));
        userInfo.setSurname(rs.getString("surname"));
        userInfo.setFatherhood(rs.getString("fatherhood"));
        userInfo.setBirthday(rs.getDate("birthday").toLocalDate());
        userInfo.setEmail(rs.getString("email"));
        userInfo.setPassword(rs.getString("password"));
        return userInfo;
    }

}
