package solvd.laba.ermakovich.ha.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import solvd.laba.ermakovich.ha.web.dto.group.onCreateAppointment;

import java.time.LocalDateTime;

@Data
public class AppointmentDto {
    private long id;
    @NotNull(groups = onCreateAppointment.class)
    @Valid
    private DoctorDto doctorDto;
    private PatientCardDto patientCardDto;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Future(message = "appointment start time can`t be in the future")
    private LocalDateTime start;
}
