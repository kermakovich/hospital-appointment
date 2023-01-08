package solvd.laba.ermakovich.ha.domain;

import lombok.Data;
import solvd.laba.ermakovich.ha.domain.card.PatientCard;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;

import java.time.LocalDateTime;

@Data
public class Appointment {
    private long id;
    private Doctor doctor;
    private PatientCard patientCard;
    private LocalDateTime start;
}
