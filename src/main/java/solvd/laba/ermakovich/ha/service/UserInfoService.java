package solvd.laba.ermakovich.ha.service;

import solvd.laba.ermakovich.ha.domain.UserInfo;

public interface UserInfoService {

    UserInfo create(UserInfo userInfo);

    UserInfo findByEmail(String email);

    UserInfo findById(Long id);
}
