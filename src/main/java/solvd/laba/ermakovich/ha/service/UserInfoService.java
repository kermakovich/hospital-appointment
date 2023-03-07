package solvd.laba.ermakovich.ha.service;

import solvd.laba.ermakovich.ha.domain.UserInfo;

import java.util.UUID;

public interface UserInfoService {

    UserInfo create(UserInfo userInfo);

    UserInfo findByEmail(String email);

    Boolean isExistByExternalId(UUID externalId);

}
