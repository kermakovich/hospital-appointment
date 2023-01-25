package solvd.laba.ermakovich.ha.domain.jwt;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtResponse {

    private JwtAccess access;
    private Refresh refresh;

}
