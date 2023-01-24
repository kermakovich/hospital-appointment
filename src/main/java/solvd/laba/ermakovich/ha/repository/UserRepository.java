package solvd.laba.ermakovich.ha.repository;

import org.apache.ibatis.annotations.Mapper;
import solvd.laba.ermakovich.ha.domain.UserInfo;

import java.util.Optional;

@Mapper
public interface UserRepository {

    void save(UserInfo userInfo);

    boolean isExistByEmail(String email);

    Optional<UserInfo> findByEmail(String email);

}
