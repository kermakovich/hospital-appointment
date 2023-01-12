package solvd.laba.ermakovich.ha.domain.doctor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvailableSlots {

    private Long doctorId;
    private LocalDate date;
    private List<LocalTime> timeList;

}
