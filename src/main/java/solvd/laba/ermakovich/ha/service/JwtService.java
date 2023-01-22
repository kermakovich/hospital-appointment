package solvd.laba.ermakovich.ha.service;

import org.springframework.security.core.Authentication;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.domain.jwt.JwtAccess;
import solvd.laba.ermakovich.ha.web.security.jwt.JwtUserDetails;

public interface JwtService {

    JwtUserDetails parseToken(String token);

    String generateRefreshToken(UserInfo user);

    JwtAccess generateAccessToken(UserInfo user);

    Authentication getAuthentication(String token);

    boolean isAccessToken(JwtUserDetails jwtUserDetails);

}
