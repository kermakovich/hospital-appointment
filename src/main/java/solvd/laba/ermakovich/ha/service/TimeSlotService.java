package solvd.laba.ermakovich.ha.service;

import solvd.laba.ermakovich.ha.domain.doctor.AvailableSlots;

import java.time.LocalDate;

public interface TimeSlotService {

    AvailableSlots getSchedule(long id, LocalDate date);

}
