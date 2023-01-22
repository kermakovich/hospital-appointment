package solvd.laba.ermakovich.ha.domain.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class JwtAccess {

    private String token;
    private Instant expirationTime;

}
