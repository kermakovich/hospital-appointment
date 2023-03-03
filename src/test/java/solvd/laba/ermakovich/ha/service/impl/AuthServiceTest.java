package solvd.laba.ermakovich.ha.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.domain.UserRole;
import solvd.laba.ermakovich.ha.domain.exception.AuthException;
import solvd.laba.ermakovich.ha.domain.jwt.Authentication;
import solvd.laba.ermakovich.ha.domain.jwt.JwtAccess;
import solvd.laba.ermakovich.ha.domain.jwt.JwtResponse;
import solvd.laba.ermakovich.ha.domain.jwt.Refresh;
import solvd.laba.ermakovich.ha.service.JwtService;
import solvd.laba.ermakovich.ha.service.UserInfoService;
import solvd.laba.ermakovich.ha.web.security.jwt.JwtUserDetails;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserInfoService userInfoService;

    @Mock
    private JwtService jwtService;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private AuthServiceImpl authService;


    @Test
    void verifyLoginSuccessfulTest() {
        given(userInfoService.findByEmail(anyString())).willReturn(getUserInfo());
        given(encoder.matches(anyString(), anyString())).willReturn(true);
        given(jwtService.generateAccessToken(any(UserInfo.class))).willReturn(getJwtAccess());
        given(jwtService.generateRefreshToken(any(UserInfo.class))).willReturn(getRefresh());

        JwtResponse actualResponse = authService.login(getAuthentication());

        assertEquals(getJwtResponse(), actualResponse, "responses are not equal");
    }

    @Test
    void verifyLoginThrowsAuthExceptionTest() {
        given(userInfoService.findByEmail(anyString())).willReturn(getUserInfo());
        given(encoder.matches(any(CharSequence.class), anyString())).willReturn(false);

        assertThrows(AuthException.class, () -> authService.login(getAuthentication()), "AuthException wasn`t thrown");
        verify(jwtService, never()).generateAccessToken(any(UserInfo.class));
        verify(jwtService, never()).generateRefreshToken(any(UserInfo.class));
    }

    @Test
    void verifyRefreshSuccessfulTest() {
        given(jwtService.parseToken(anyString())).willReturn(getJwtUserDetails());
        given(userInfoService.findByEmail(anyString())).willReturn(getUserInfo());
        given(jwtService.generateAccessToken(any(UserInfo.class))).willReturn(getJwtAccess());
        given(jwtService.generateRefreshToken(any(UserInfo.class))).willReturn(getRefresh());

        JwtResponse actualResponse = authService.refresh(getRefresh());

        assertEquals(getJwtResponse(), actualResponse, "responses are not equal");
    }

    @Test
    void verifyRefreshThrowsAuthExceptionTest() {
        given(jwtService.parseToken(anyString())).willReturn(getJwtUserDetails());
        UserInfo wrongUserInfo = getUserInfo();
        wrongUserInfo.setPassword("123444");
        given(userInfoService.findByEmail(anyString())).willReturn(wrongUserInfo);

        assertThrows(AuthException.class, () -> authService.refresh(getRefresh()), "AuthException wasn`t thrown");
        verify(jwtService, never()).generateAccessToken(any(UserInfo.class));
        verify(jwtService, never()).generateRefreshToken(any(UserInfo.class));
    }


    private Authentication getAuthentication() {
        return new Authentication("admin", "1234");
    }

    private UserInfo getUserInfo() {
        return new UserInfo(
                1L,
                UUID.randomUUID(),
                "name",
                "surname",
                "fatherhood",
                LocalDate.of(2001, 1, 1),
                getAuthentication().getEmail(),
                "$2a$10$UmzxqbNCl9aBTtmkdAKajeXFrir6AHD6I3kE/MiLWU.W1RWLMvnYq",
                UserRole.PATIENT );
    }

    private JwtUserDetails getJwtUserDetails() {
        UserInfo userInfo = getUserInfo();
        return new JwtUserDetails(
                userInfo.getId(),
                userInfo.getPassword(),
                userInfo.getEmail(),
                true,
                List.of(UserRole.DOCTOR)
        );
    }

    private Refresh getRefresh() {
        return new Refresh("refresh");
    }

    private JwtAccess getJwtAccess() {
        return new JwtAccess("access", Instant.EPOCH);
    }

    private JwtResponse getJwtResponse() {
        return new JwtResponse(getJwtAccess(), getRefresh());
    }

}