package solvd.laba.ermakovich.ha.domain;

import lombok.Data;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;

@Data
public class Review {
    private long id;
    private Doctor doctor;
    private Patient patient;
    private String description;

}
