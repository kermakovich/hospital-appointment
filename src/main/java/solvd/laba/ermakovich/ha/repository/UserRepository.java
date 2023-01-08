package solvd.laba.ermakovich.ha.repository;

import solvd.laba.ermakovich.ha.domain.UserInfo;

import java.util.Optional;

public interface UserRepository {

    void save(UserInfo userInfo);

    Optional<UserInfo> findByEmail(String email);
}
