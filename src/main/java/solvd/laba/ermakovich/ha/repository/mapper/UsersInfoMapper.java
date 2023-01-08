package solvd.laba.ermakovich.ha.repository.mapper;

import solvd.laba.ermakovich.ha.domain.UserInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UsersInfoMapper {

    public static UserInfo map(ResultSet rs) {
        try {
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
        catch (
    SQLException e) {
        throw new RuntimeException(e);
    }
}
}
