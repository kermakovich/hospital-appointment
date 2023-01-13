package solvd.laba.ermakovich.ha.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SearchAppointmentCriteria {

    LocalDate date;
    AppointmentStatus status;

}
