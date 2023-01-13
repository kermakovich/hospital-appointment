package solvd.laba.ermakovich.ha.web.dto;

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
public class PatientCardDto {

    private Long id;
    private PatientDto patientDto;
    private UUID number;
    private LocalDate registryDate;

}
