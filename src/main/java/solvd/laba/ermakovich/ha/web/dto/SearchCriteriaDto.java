package solvd.laba.ermakovich.ha.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import solvd.laba.ermakovich.ha.domain.doctor.Specialization;
import solvd.laba.ermakovich.ha.domain.hospital.Department;

@Getter
@Setter
@Schema(description = "criteria for searching doctor")
public class SearchCriteriaDto {

    @Schema(description = "doctor department")
    private Department department;

    @Schema(description = "doctor specialization")
    private Specialization specialization;

}
