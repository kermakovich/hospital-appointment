package solvd.laba.ermakovich.ha.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import solvd.laba.ermakovich.ha.domain.AppointmentStatus;

import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "criteria for filter appointments")
public class SearchAppointmentCriteriaDto {

    @Schema(description = "particular date for appointments", format = "dd-MM-yyyy")
    private LocalDate date;

    @Schema(description = "status for appointments")
    private AppointmentStatus status;

}
