package solvd.laba.ermakovich.ha.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    private Long id;
    private Doctor doctor;
    private Patient patient;
    private String description;

}
