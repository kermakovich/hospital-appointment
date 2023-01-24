package solvd.laba.ermakovich.ha.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import solvd.laba.ermakovich.ha.web.dto.group.onCreateAppointment;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "appointment info")
public class AppointmentDto {

    @Schema(description = "appointment id")
    private Long id;

    @NotNull( groups = onCreateAppointment.class, message = "doctor can`t be null")
    @Valid
    private DoctorDto doctorDto;

    private PatientCardDto patientCardDto;

    @NotNull(groups = onCreateAppointment.class, message = "start time can`t be null")
    @Future(message = "appointment start time can`t be in the past")
    @Schema(description = "time and date of start appointment")
    private LocalDateTime start;

}
