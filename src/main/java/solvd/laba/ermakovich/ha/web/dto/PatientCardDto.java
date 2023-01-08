package solvd.laba.ermakovich.ha.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientCardDto {

    private PatientDto patient;
    private UUID number;
    private LocalDate registryDate;

}
