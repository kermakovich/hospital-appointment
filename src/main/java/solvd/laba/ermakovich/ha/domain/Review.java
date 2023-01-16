package solvd.laba.ermakovich.ha.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    private Long id;
    private Doctor doctor;
    private Patient patient;
    private String description;

}
