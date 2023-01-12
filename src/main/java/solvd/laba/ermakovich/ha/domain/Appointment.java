package solvd.laba.ermakovich.ha.domain;

import lombok.Getter;
import lombok.Setter;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;

import java.time.LocalDateTime;

@Setter
@Getter
public class Appointment {

    private Long id;
    private Doctor doctor;
    private PatientCard patientCard;
    private LocalDateTime start;

}
