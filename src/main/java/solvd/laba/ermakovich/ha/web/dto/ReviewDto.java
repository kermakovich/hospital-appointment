package solvd.laba.ermakovich.ha.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import solvd.laba.ermakovich.ha.web.dto.group.onCreateReview;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewDto {
    private long id;
    @NotNull(groups = onCreateReview.class, message = "doctorDto can`t be null")
    @Valid
    private DoctorDto doctorDto;
    @NotNull(groups = onCreateReview.class, message = "patientDto can`t be null")
    @Valid
    private PatientDto patientDto;
    @NotNull(message = "review`s description should not be empty")
    @NotBlank(message = "description can`t be empty")
    private String description;
}

