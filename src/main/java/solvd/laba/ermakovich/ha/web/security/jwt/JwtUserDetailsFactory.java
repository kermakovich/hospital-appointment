package solvd.laba.ermakovich.ha.web.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.domain.UserRole;

import java.util.Collections;
import java.util.List;
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

    private static List<GrantedAuthority> mapToGrantedAuthority(List<UserRole> userRoles) {
        return userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.name()))
                .collect(Collectors.toList());
    }

}
