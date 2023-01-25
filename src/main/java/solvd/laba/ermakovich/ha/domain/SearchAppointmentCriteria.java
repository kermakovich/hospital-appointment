package solvd.laba.ermakovich.ha.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchAppointmentCriteria {

    private LocalDate date;
    private AppointmentStatus status;

}
