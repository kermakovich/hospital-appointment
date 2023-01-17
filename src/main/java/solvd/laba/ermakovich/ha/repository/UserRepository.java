package solvd.laba.ermakovich.ha.repository;

import org.apache.ibatis.annotations.Mapper;
import solvd.laba.ermakovich.ha.domain.UserInfo;

@Mapper
public interface UserRepository {

    void save(UserInfo userInfo);

    boolean isExistByEmail(String email);

}
