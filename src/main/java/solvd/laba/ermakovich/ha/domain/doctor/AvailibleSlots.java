package solvd.laba.ermakovich.ha.domain.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
public class AvailibleSlots {
    private Long doctorId;
    private LocalDate date;
    private List<LocalTime> timeList;

}
