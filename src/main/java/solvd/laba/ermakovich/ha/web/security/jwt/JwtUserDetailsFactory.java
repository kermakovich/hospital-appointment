package solvd.laba.ermakovich.ha.web.security.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.domain.UserRole;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JwtUserDetailsFactory {

    public static JwtUserDetails create(UserInfo user) {

        return new JwtUserDetails(
                user.getId(),
                user.getPassword(),
                user.getEmail(),
                true,
                mapToGrantedAuthority(Collections.singletonList(user.getRole())));
    }

    public static JwtUserDetails create(Claims claims) {
        UserRole role = null;
        String roleClaim = claims.get("role", String.class);
        if (roleClaim != null) {
            role = UserRole.valueOf(roleClaim);
        }

        return new JwtUserDetails(
                claims.get("id", Long.class),
                claims.get("password", String.class),
                claims.getSubject(),
                true,
                mapToGrantedAuthority(Collections.singletonList(role)));
    }



    private static List<GrantedAuthority> mapToGrantedAuthority(List<UserRole> userRoles) {
        return userRoles.stream()
                .filter(Objects::nonNull)
                .map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.name()))
                .collect(Collectors.toList());
    }

}
