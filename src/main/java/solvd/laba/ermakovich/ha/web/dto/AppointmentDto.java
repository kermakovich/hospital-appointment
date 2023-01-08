package solvd.laba.ermakovich.ha.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDto {
    private long id;
    private DoctorDto doctorDto;
    private PatientCardDto patientCardDto;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime start;
}
