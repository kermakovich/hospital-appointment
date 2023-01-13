package solvd.laba.ermakovich.ha.web.dto;

import lombok.Getter;
import lombok.Setter;
import solvd.laba.ermakovich.ha.domain.AppointmentStatus;

import java.time.LocalDate;

@Getter
@Setter
public class SearchAppointmentCriteriaDto {

    private LocalDate date;
    private AppointmentStatus status;

}
