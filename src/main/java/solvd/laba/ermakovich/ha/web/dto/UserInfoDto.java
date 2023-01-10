package solvd.laba.ermakovich.ha.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import solvd.laba.ermakovich.ha.web.dto.group.onCreate;
import solvd.laba.ermakovich.ha.web.dto.group.onCreateAppointment;
import solvd.laba.ermakovich.ha.web.dto.group.onCreateReview;

import java.time.LocalDate;


@Getter
@Setter
public abstract class UserInfoDto {
    @NotNull(groups = {onCreateAppointment.class, onCreateReview.class},
            message = "user id can not be null")
    private Long id;
    @NotNull(groups = onCreate.class, message = "name can`t be null")
    @Size(min = 1, max = 35, message = "name length should be in 1..35")
    private String name;

    @NotNull(groups = onCreate.class, message = "surname can`t be null")
    @Size(min = 1, max = 35, message = "surname length should be in 1..35", groups = onCreate.class)
    private String surname;

    @NotNull(groups = onCreate.class, message = "fatherhood can`t be null")
    @Size(min = 1, max = 35, message = "fatherhood length should be in 1..35", groups = onCreate.class)
    private String fatherhood;

    @NotNull(groups = onCreate.class, message = "birthday can`t be null")
    @Past(groups = onCreate.class, message = "birthday can`t be in the future")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthday;

    @NotNull(groups = onCreate.class, message = "email can`t be null")
    @Size(min = 3, max = 320, groups = onCreate.class, message = "email length should be in 3..320")
    private String email;

    @NotNull( groups = onCreate.class, message = "password can`t be null")
    @Size(min = 4, max = 20, groups = onCreate.class, message = "password length should be in 4..20")
    private String password;
}
