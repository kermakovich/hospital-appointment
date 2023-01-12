package solvd.laba.ermakovich.ha.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientCard {

    private Long id;
    private Patient patient;
    private UUID number;
    private LocalDate registryDate;

}
