package solvd.laba.ermakovich.ha.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Schema(description = "available doctor`s  time slots for particular day")
public class AvailableSlotsDto {

    @Schema(description = "doctor id")
    private Long doctorId;

    @Schema(description = "date for retrieve schedule")
    private LocalDate date;

    @Schema(description = "list of time slots")
    private List<LocalTime> timeList;

}
