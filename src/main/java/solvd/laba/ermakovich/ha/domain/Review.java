package solvd.laba.ermakovich.ha.domain;

import lombok.Getter;
import lombok.Setter;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;

@Setter
@Getter
public class Review {

    private Long id;
    private Doctor doctor;
    private Patient patient;
    private String description;

}
