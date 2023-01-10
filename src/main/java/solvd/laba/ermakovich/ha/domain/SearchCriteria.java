package solvd.laba.ermakovich.ha.domain;

import lombok.Getter;
import lombok.Setter;
import solvd.laba.ermakovich.ha.domain.doctor.Specialization;
import solvd.laba.ermakovich.ha.domain.hospital.Department;

@Setter
@Getter
public class SearchCriteria {

    private Department department;

    private Specialization specialization;

}
