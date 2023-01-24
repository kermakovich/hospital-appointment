package solvd.laba.ermakovich.ha.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum UserRole implements GrantedAuthority {

    ADMIN("ADMIN"),
    PATIENT("PATIENT"),
    DOCTOR("DOCTOR");

    private final String value;

    @Override
    public String getAuthority() {
        return "ROLE_" + value;
    }

}
