package solvd.laba.ermakovich.ha.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import solvd.laba.ermakovich.ha.domain.UserRole;
import solvd.laba.ermakovich.ha.web.dto.group.onCreate;
import solvd.laba.ermakovich.ha.web.dto.group.onCreateAppointment;
import solvd.laba.ermakovich.ha.web.dto.group.onCreateReview;

import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@Schema(description = "information about user")
public abstract class UserInfoDto {

    @NotNull(groups = {onCreateAppointment.class, onCreateReview.class},
            message = "can not be null")
    @Schema(description = "user id")
    private Long id;

    @Schema(description = "user external id")
    private UUID externalId;


    @NotBlank(groups = onCreate.class, message = "can`t be empty")
    @Size(min = 1, max = 35, message = "length should be in 1..35")
    @Schema(description = "user name")
    private String name;

    @NotBlank(groups = onCreate.class, message = "can`t be empty")
    @Size(min = 1, max = 35, message = "length should be in 1..35", groups = onCreate.class)
    @Schema(description = "user surname")
    private String surname;

    @NotBlank(groups = onCreate.class, message = "can`t be empty")
    @Size(min = 1, max = 35, message = "length should be in 1..35", groups = onCreate.class)
    @Schema(description = "user fatherhood")
    private String fatherhood;

    @NotNull(groups = onCreate.class, message = "can`t be null")
    @Past(groups = onCreate.class, message = "can`t be in the future")
    @Schema(description = "user birthday", type = "string", example ="20-01-2000" )
    private LocalDate birthday;

    @NotBlank(groups = onCreate.class, message = "can`t be empty")
    @Size(min = 3, max = 320, groups = onCreate.class, message = "length should be in 3..320")
    @Schema(description = "user email")
    private String email;

    @NotBlank(groups = onCreate.class, message = "can`t be empty")
    @Size(min = 4, max = 20, groups = onCreate.class, message = "length should be in 4..20")
    @Schema(description = "user password")
    private String password;

    @NotNull(groups = onCreate.class, message = "can`t be null")
    @Schema(description = "user role"
    )
    private UserRole role;

}
