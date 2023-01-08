package solvd.laba.ermakovich.ha.domain.card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import solvd.laba.ermakovich.ha.domain.Patient;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientCard {

    private Patient patient;
    private UUID number;
    private LocalDate registryDate;
}
