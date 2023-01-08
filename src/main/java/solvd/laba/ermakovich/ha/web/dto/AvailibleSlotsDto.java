package solvd.laba.ermakovich.ha.web.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class AvailibleSlotsDto {

    private Long doctorId;
    private LocalDate date;
    private List<LocalTime> timeList;
}
