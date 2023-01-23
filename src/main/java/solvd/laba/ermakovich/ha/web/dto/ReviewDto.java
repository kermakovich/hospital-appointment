package solvd.laba.ermakovich.ha.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import solvd.laba.ermakovich.ha.web.dto.group.onCreateReview;

@Getter
@Setter
@Schema(description = "doctor`s review")
public class ReviewDto {

    @Schema(description = "review id")
    private Long id;

    @NotNull(groups = onCreateReview.class, message = "can`t be null")
    @Valid
    private DoctorDto doctorDto;

    @NotNull(groups = onCreateReview.class, message = "can`t be null")
    @Valid
    private PatientDto patientDto;

    @NotBlank(message = "can`t be empty")
    @Schema(description = "review description")
    private String description;

}

