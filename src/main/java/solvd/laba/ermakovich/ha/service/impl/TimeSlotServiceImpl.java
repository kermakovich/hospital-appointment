package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.doctor.AvailableSlots;
import solvd.laba.ermakovich.ha.domain.exception.ResourceDoesNotExistException;
import solvd.laba.ermakovich.ha.domain.hospital.OpeningHours;
import solvd.laba.ermakovich.ha.service.AppointmentService;
import solvd.laba.ermakovich.ha.service.DoctorService;
import solvd.laba.ermakovich.ha.service.TimeSlotService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TimeSlotServiceImpl implements TimeSlotService {

    private final DoctorService doctorService;
    private final OpeningHours openingHours;
    private final AppointmentService appointmentService;

    @Override
    @Transactional(readOnly = true)
    public AvailableSlots retrieveSchedule(long id, LocalDate date) {
        if (!doctorService.isExistById(id)) {
            throw new ResourceDoesNotExistException("doctor with id: " + id + "doesn`t exist");
        }
        int steps = (int) openingHours.start.until(openingHours.finish, ChronoUnit.HOURS);
        List<LocalTime> startTimeSlots = new java.util.ArrayList<>(
                IntStream.rangeClosed(0, steps - 1)
                        .mapToObj(buildTimeSlots())
                        .toList());
        List<LocalTime> timeList = appointmentService.getTimeSlotsByDoctorIdAndDate(id, date);
        startTimeSlots.removeAll(timeList);
        return new AvailableSlots(id, date, startTimeSlots);
    }

    private IntFunction<LocalTime> buildTimeSlots() {
        return n -> openingHours.start.plus(openingHours.minutesRange * n, ChronoUnit.MINUTES);
    }

}
