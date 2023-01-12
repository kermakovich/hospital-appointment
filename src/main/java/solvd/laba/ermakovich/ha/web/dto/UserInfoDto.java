package solvd.laba.ermakovich.ha.web.dto;

import jakarta.validation.constraints.NotBlank;
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
            message = "can not be null")
    private Long id;

    @NotBlank(groups = onCreate.class, message = "can`t be empty")
    @Size(min = 1, max = 35, message = "length should be in 1..35")
    private String name;

    @NotBlank(groups = onCreate.class, message = "can`t be empty")
    @Size(min = 1, max = 35, message = "length should be in 1..35", groups = onCreate.class)
    private String surname;

    @NotBlank(groups = onCreate.class, message = "can`t be empty")
    @Size(min = 1, max = 35, message = "length should be in 1..35", groups = onCreate.class)
    private String fatherhood;

    @NotNull(groups = onCreate.class, message = "can`t be null")
    @Past(groups = onCreate.class, message = "can`t be in the future")
    private LocalDate birthday;

    @NotBlank(groups = onCreate.class, message = "can`t be empty")
    @Size(min = 3, max = 320, groups = onCreate.class, message = "length should be in 3..320")
    private String email;

    @NotBlank(groups = onCreate.class, message = "can`t be empty")
    @Size(min = 4, max = 20, groups = onCreate.class, message = "length should be in 4..20")
    private String password;

}
