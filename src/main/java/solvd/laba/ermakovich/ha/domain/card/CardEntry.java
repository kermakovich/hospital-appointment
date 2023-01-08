package solvd.laba.ermakovich.ha.domain.card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CardEntry {

    private Long id;
    private PatientCard card;
    private Doctor doctor;
    private String diagnose;
    private String treatment;
}
