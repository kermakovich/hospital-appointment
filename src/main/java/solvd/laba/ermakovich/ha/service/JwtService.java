package solvd.laba.ermakovich.ha.service;

import org.springframework.security.core.Authentication;
import solvd.laba.ermakovich.ha.domain.UserInfo;

public interface JwtService {

    boolean validateToken(String token);

    Authentication getAuthentication(String token);

    String generateRefreshToken(UserInfo user);

    String generateAccessToken(UserInfo user);

    String getSubject(String token);

}
