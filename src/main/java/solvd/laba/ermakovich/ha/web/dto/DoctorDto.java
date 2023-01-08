package solvd.laba.ermakovich.ha.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import solvd.laba.ermakovich.ha.domain.doctor.Specialization;
import solvd.laba.ermakovich.ha.domain.hospital.Department;


@Data
public class DoctorDto extends UserInfoDto {
    @NotNull
    private Department department;
    @NotNull
    private Specialization specialization;

}
