package solvd.laba.ermakovich.ha.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientCard {

    private Long id;
    private Patient patient;
    private UUID number;
    private LocalDate registryDate;

}
