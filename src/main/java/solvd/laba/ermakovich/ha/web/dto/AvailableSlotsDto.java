package solvd.laba.ermakovich.ha.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class AvailableSlotsDto {

    private Long doctorId;
    private LocalDate date;
    private List<LocalTime> timeList;
}
