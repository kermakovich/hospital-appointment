package solvd.laba.ermakovich.ha.repository.jdbc.mapper;

import org.mapstruct.Mapper;
import solvd.laba.ermakovich.ha.domain.UserInfo;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {

    UserInfo mapToUserInfo(UserInfo user);

}
