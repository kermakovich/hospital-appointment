package solvd.laba.ermakovich.ha.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewDto {
    private long id;
    private DoctorDto doctorDto;
    private PatientDto patientDto;
    @NotNull(message = "review`s description should not be empty")
    private String description;
}
