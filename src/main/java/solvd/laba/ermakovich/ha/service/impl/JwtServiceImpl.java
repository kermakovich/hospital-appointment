package solvd.laba.ermakovich.ha.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.domain.jwt.JwtAccess;
import solvd.laba.ermakovich.ha.service.JwtService;
import solvd.laba.ermakovich.ha.service.UserInfoService;
import solvd.laba.ermakovich.ha.service.property.JwtProperties;
import solvd.laba.ermakovich.ha.web.security.jwt.JwtUserDetails;
import solvd.laba.ermakovich.ha.web.security.jwt.JwtUserDetailsFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;
    private final UserInfoService userInfoService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Override
    public JwtUserDetails parseToken(String token) {
       Claims claims =  Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
       return JwtUserDetailsFactory.create(claims);
    }

    @Override
    public Authentication getAuthentication(String token) {
        JwtUserDetails jwtUserDetails = parseToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUserDetails.getEmail());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Override
    public boolean isAccessToken(JwtUserDetails jwtUserDetails) {
        return jwtUserDetails.getId() != null && jwtUserDetails.getPassword() == null;
    }

    @Override
    public String generateRefreshToken(UserInfo user) {
        final Instant refreshExpiration = Instant.now().plus(jwtProperties.getRefresh(), ChronoUnit.HOURS);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("password", user.getPassword())
                .setExpiration(java.util.Date.from(refreshExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
                .compact();
    }

    @Override
    public JwtAccess generateAccessToken(UserInfo user) {
        final Instant accessExpiration = Instant.now().plus(jwtProperties.getAccess(), ChronoUnit.MINUTES);
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole())
                .claim("id", user.getId())
                .setExpiration(Date.from(accessExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
                .compact();
        return new JwtAccess(token, accessExpiration);
    }

}
