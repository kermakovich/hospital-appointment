package solvd.laba.ermakovich.ha.repository;

import solvd.laba.ermakovich.ha.domain.UserInfo;

public interface UserRepository {

    void save(UserInfo userInfo);

    boolean isExistByEmail(String email);
}
