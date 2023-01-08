package solvd.laba.ermakovich.ha.domain.doctor;

import lombok.*;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.domain.hospital.Department;


@Data
public class Doctor extends UserInfo {

    private Department department;
    private Specialization specialization;

}
