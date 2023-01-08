package solvd.laba.ermakovich.ha.service;

import solvd.laba.ermakovich.ha.domain.UserInfo;

import java.util.Optional;

public interface UserInfoService {

    UserInfo save(UserInfo userInfo);

    Optional<UserInfo> findByEmail(String email);
}
