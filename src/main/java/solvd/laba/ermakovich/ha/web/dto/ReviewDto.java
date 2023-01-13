package solvd.laba.ermakovich.ha.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import solvd.laba.ermakovich.ha.web.dto.group.onCreateReview;

@Getter
@Setter
public class ReviewDto {

    private Long id;

    @NotNull(groups = onCreateReview.class, message = "can`t be null")
    @Valid
    private DoctorDto doctorDto;

    @NotNull(groups = onCreateReview.class, message = "can`t be null")
    @Valid
    private PatientDto patientDto;

    @NotBlank(message = "can`t be empty")
    private String description;

}

