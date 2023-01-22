package solvd.laba.ermakovich.ha.web.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAccessDto {

    private String token;
    private Instant expirationTime;

}
