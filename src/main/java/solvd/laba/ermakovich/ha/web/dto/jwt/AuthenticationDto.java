package solvd.laba.ermakovich.ha.web.dto.jwt;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import solvd.laba.ermakovich.ha.web.dto.group.onCreate;

@Getter
@Setter
public class AuthenticationDto {

    @NotBlank(groups = onCreate.class, message = "can`t be empty")
    @Size(min = 3, max = 320, groups = onCreate.class, message = "length should be in 3..320")
    private String email;

    @NotBlank(groups = onCreate.class, message = "can`t be empty")
    @Size(min = 4, max = 20, groups = onCreate.class, message = "length should be in 4..20")
    private String password;

}
