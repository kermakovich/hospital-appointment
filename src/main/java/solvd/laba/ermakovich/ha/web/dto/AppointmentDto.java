package solvd.laba.ermakovich.ha.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import solvd.laba.ermakovich.ha.web.dto.group.onCreateAppointment;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentDto {
    private long id;
    @NotNull(groups = onCreateAppointment.class)
    @Valid
    private DoctorDto doctorDto;
    private PatientCardDto patientCardDto;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Future(message = "appointment start time can`t be in the past")
    private LocalDateTime start;
}
