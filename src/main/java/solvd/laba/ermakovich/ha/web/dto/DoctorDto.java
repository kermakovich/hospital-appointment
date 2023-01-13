package solvd.laba.ermakovich.ha.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import solvd.laba.ermakovich.ha.domain.doctor.Specialization;
import solvd.laba.ermakovich.ha.domain.hospital.Department;
import solvd.laba.ermakovich.ha.web.dto.group.onCreate;


@Getter
@Setter
public class DoctorDto extends UserInfoDto {

    @NotNull(message = "can`t be null", groups = onCreate.class)
    private Department department;

    @NotNull(message = "can`t be null", groups = onCreate.class)
    private Specialization specialization;

    private Integer cabinet;

}
