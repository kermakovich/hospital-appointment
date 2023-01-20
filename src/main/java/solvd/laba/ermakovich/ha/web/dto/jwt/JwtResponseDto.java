package solvd.laba.ermakovich.ha.web.dto.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponseDto {

    private String access;
    private String refresh;

}
