package solvd.laba.ermakovich.ha.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "patient card info")
public class PatientCardDto {

    @Schema(description = "patient card id")
    private Long id;

    private PatientDto patientDto;

    @Schema(description = "patient card number, generated when card is created")
    private UUID number;

    @Schema(description = "registration date for patient card")
    private LocalDate registryDate;

}
