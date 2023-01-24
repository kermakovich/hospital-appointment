package solvd.laba.ermakovich.ha.domain.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {

    private JwtAccess access;
    private String refresh;

}
