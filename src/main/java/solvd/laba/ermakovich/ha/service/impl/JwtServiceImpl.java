package solvd.laba.ermakovich.ha.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.service.JwtService;
import solvd.laba.ermakovich.ha.service.UserInfoService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${security.jwt.expiration-time-access}")
    private Long expirationTimeAccess;

    @Value("${security.jwt.expiration-time-refresh}")
    private Long expirationTimeRefresh;

    @Value("${security.jwt.secret}")
    private String secretKey;

    private final UserInfoService userInfoService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Override
    public boolean validateToken(String token) {
        Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
        return true;
    }

    @Override
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Override
    public String getSubject(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @Override
    public String generateRefreshToken(UserInfo user) {
        final Instant refreshExpiration = Instant.now().plus(expirationTimeRefresh, ChronoUnit.MINUTES);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(java.util.Date.from(refreshExpiration))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    @Override
    public String generateAccessToken(UserInfo user) {
        final Instant accessExpiration = Instant.now().plus(expirationTimeAccess, ChronoUnit.MINUTES);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(Date.from(accessExpiration))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .claim("role", user.getRole())
                .claim("id", user.getId())
                .compact();
    }

}
