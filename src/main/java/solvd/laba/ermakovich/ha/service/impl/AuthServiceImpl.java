package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import solvd.laba.ermakovich.ha.domain.jwt.Authentication;
import solvd.laba.ermakovich.ha.domain.jwt.JwtResponse;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.domain.exception.AuthException;
import solvd.laba.ermakovich.ha.domain.jwt.Refresh;
import solvd.laba.ermakovich.ha.service.AuthService;
import solvd.laba.ermakovich.ha.service.JwtService;
import solvd.laba.ermakovich.ha.service.UserInfoService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserInfoService userInfoService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public JwtResponse login(Authentication authentication) {
        UserInfo user = userInfoService.findByEmail(authentication.getEmail());
        if (!passwordEncoder.matches(authentication.getPassword(), user.getPassword())) {
            throw new AuthException("wrong password");
        }
        final String accessToken = jwtService.generateAccessToken(user);
        final String refreshToken = jwtService.generateRefreshToken(user);
        return new JwtResponse(accessToken, refreshToken);
    }

    @Override
    public JwtResponse refresh(Refresh refresh) {
        String refreshToken = refresh.getToken();
        if (jwtService.validateToken(refreshToken)) {
            final String email = jwtService.getSubject(refreshToken);
            final UserInfo user = userInfoService.findByEmail(email);
            final String accessToken = jwtService.generateAccessToken(user);
            final String newRefreshToken = jwtService.generateRefreshToken(user);
            return new JwtResponse(accessToken, newRefreshToken);
        }
        throw new AuthException("refresh token isn`t valid");
    }

}
