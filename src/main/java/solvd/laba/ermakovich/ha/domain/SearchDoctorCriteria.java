package solvd.laba.ermakovich.ha.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import solvd.laba.ermakovich.ha.domain.doctor.Specialization;
import solvd.laba.ermakovich.ha.domain.hospital.Department;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchDoctorCriteria {

    private Department department;
    private Specialization specialization;

}
