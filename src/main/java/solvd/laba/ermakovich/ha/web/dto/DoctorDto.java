package solvd.laba.ermakovich.ha.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import solvd.laba.ermakovich.ha.domain.doctor.Specialization;
import solvd.laba.ermakovich.ha.domain.hospital.Department;
import solvd.laba.ermakovich.ha.web.dto.group.onCreate;


@Getter
@Setter
@Schema(description = "doctor info")
public class DoctorDto extends UserInfoDto {

    @NotNull(message = "can`t be null", groups = onCreate.class)
    @Schema(description = "doctor department")
    private Department department;

    @NotNull(message = "can`t be null", groups = onCreate.class)
    @Schema(description = "doctor specialization")
    private Specialization specialization;

    @Schema(description = "doctor cabinet")
    private Integer cabinet;

}
