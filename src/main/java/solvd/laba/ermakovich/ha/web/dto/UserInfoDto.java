package solvd.laba.ermakovich.ha.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class UserInfoDto {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String fatherhood;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull
    private LocalDate birthday;
    @NotNull
    private String email;

    @NotNull
    private String password;

}
