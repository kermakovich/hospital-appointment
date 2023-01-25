package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import solvd.laba.ermakovich.ha.domain.jwt.Authentication;
import solvd.laba.ermakovich.ha.domain.jwt.JwtAccess;
import solvd.laba.ermakovich.ha.domain.jwt.JwtResponse;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.domain.exception.AuthException;
import solvd.laba.ermakovich.ha.domain.jwt.Refresh;
import solvd.laba.ermakovich.ha.service.AuthService;
import solvd.laba.ermakovich.ha.service.JwtService;
import solvd.laba.ermakovich.ha.service.UserInfoService;
import solvd.laba.ermakovich.ha.web.security.jwt.JwtUserDetails;

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
        final JwtAccess access = jwtService.generateAccessToken(user);
        final Refresh refreshToken = jwtService.generateRefreshToken(user);
        return new JwtResponse(access, refreshToken);
    }

    @Override
    public JwtResponse refresh(Refresh refresh) {
        String refreshToken = refresh.getToken();
        JwtUserDetails userDetails = jwtService.parseToken(refreshToken);
        final UserInfo user = userInfoService.findByEmail(userDetails.getEmail());
        if (!user.getPassword().equals(userDetails.getPassword())) {
            throw new AuthException("wrong password");
        }
        final JwtAccess access = jwtService.generateAccessToken(user);
        final Refresh newRefreshToken = jwtService.generateRefreshToken(user);
        return new JwtResponse(access, newRefreshToken);
        }
    }


