package solvd.laba.ermakovich.ha.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import solvd.laba.ermakovich.ha.domain.doctor.AvailableSlots;
import solvd.laba.ermakovich.ha.domain.exception.ResourceDoesNotExistException;
import solvd.laba.ermakovich.ha.domain.hospital.OpeningHours;
import solvd.laba.ermakovich.ha.service.AppointmentService;
import solvd.laba.ermakovich.ha.service.DoctorService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TimeSlotServiceTest {

    @Mock
    private DoctorService doctorService;

    @Mock
    private OpeningHours openingHours ;

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private TimeSlotServiceImpl timeSlotService;


    @Test
    void verifyRetrieveScheduleSuccessWithOneExistsAppointmentTest() {
        given(doctorService.isExistById(anyLong())).willReturn(true);
        ReflectionTestUtils.setField(openingHours, "start", LocalTime.of(8,0));
        ReflectionTestUtils.setField(openingHours, "finish", LocalTime.of(20,0));
        ReflectionTestUtils.setField(openingHours, "minutesRange", 60);
        given(appointmentService.getTimeSlotsByDoctorIdAndDate(anyLong(), any())).willReturn(getTimeSlot(9));

        AvailableSlots availableSlots = timeSlotService.retrieveSchedule(1, LocalDate.now().plusDays(1));

        assertEquals(getSlotsWithoutOneSlot(), availableSlots.getTimeList(), "slots are not equal");
    }

    @Test
    void verifyRetrieveScheduleThrowsResourceDoesNotExistExceptionTest() {
        given(doctorService.isExistById(anyLong())).willReturn(false);

        assertThrows(ResourceDoesNotExistException.class,
                () -> timeSlotService.retrieveSchedule(anyLong(), LocalDate.now().plusDays(1)),
                "Exception wasn`t thrown");
    }

    private List<LocalTime> getSlotsWithoutOneSlot() {
        List<LocalTime> slots = new ArrayList<>();
        slots.add(LocalTime.of(8,0));
        slots.add(LocalTime.of(10,0));
        slots.add(LocalTime.of(11,0));
        slots.add(LocalTime.of(12,0));
        slots.add(LocalTime.of(13,0));
        slots.add(LocalTime.of(14,0));
        slots.add(LocalTime.of(15,0));
        slots.add(LocalTime.of(16,0));
        slots.add(LocalTime.of(17,0));
        slots.add(LocalTime.of(18,0));
        slots.add(LocalTime.of(19,0));
        return slots;
    }

    private List<LocalTime> getTimeSlot(int hour) {
        return List.of(LocalTime.of(hour,0));
    }

}