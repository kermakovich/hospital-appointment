package solvd.laba.ermakovich.ha.web.dto;

import lombok.Getter;
import lombok.Setter;
import solvd.laba.ermakovich.ha.domain.doctor.Specialization;
import solvd.laba.ermakovich.ha.domain.hospital.Department;

@Getter
@Setter
public class SearchCriteriaDto {

    private Department department;
    private Specialization specialization;

}
