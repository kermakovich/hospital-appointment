package solvd.laba.ermakovich.ha.web.controller;

import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.domain.Review;
import solvd.laba.ermakovich.ha.domain.SearchAppointmentCriteria;
import solvd.laba.ermakovich.ha.domain.SearchDoctorCriteria;
import solvd.laba.ermakovich.ha.domain.doctor.AvailableSlots;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;
import solvd.laba.ermakovich.ha.service.AppointmentService;
import solvd.laba.ermakovich.ha.service.DoctorService;
import solvd.laba.ermakovich.ha.service.ReviewService;
import solvd.laba.ermakovich.ha.service.TimeSlotService;
import solvd.laba.ermakovich.ha.web.dto.*;
import solvd.laba.ermakovich.ha.web.dto.group.onCreate;
import solvd.laba.ermakovich.ha.web.mapper.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final TimeSlotService timeSlotService;
    private final DoctorMapper doctorMapper;
    private final AvailableSlotsMapper availableSlotsMapper;
    private final SearchCriteriaMapper searchCriteriaMapper;
    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;
    private final SearchAppointmentCriteriaMapper searchAppointmentCriteriaMapper;
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDto create(@Validated({onCreate.class, Default.class}) @RequestBody DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.dtoToEntity(doctorDto);
        doctorService.create(doctor);
        return doctorMapper.entityToDto(doctor);
    }

    @GetMapping
    public List<DoctorDto> getAll(SearchCriteriaDto searchCriteriaDto) {
        SearchDoctorCriteria criteria = searchCriteriaMapper.dtoToEntity(searchCriteriaDto);
        List<Doctor> doctors = doctorService.retrieveAllBySearchCriteria(criteria);
        return doctorMapper.entityToDto(doctors);
    }


    @GetMapping("/{id}/schedule")
    public AvailableSlotsDto getAvailableSlots(@PathVariable long id, @RequestParam LocalDate date) {
        AvailableSlots availableSlots = timeSlotService.retrieveSchedule(id, date);
        return availableSlotsMapper.entityToDto(availableSlots);
    }

    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    @GetMapping("/{doctorId}/appointments")
    public List<AppointmentDto> getAppointmentByDoctorAndCriteria(@PathVariable long doctorId, SearchAppointmentCriteriaDto criteriaDto) {
        SearchAppointmentCriteria criteria = searchAppointmentCriteriaMapper.dtoToEntity(criteriaDto);
        List<Appointment> appointments = appointmentService.retrieveAllByDoctorIdAndCriteria(doctorId, criteria);
        return appointmentMapper.entityToDto(appointments);
    }

    @GetMapping("/{doctorId}/reviews")
    public List<ReviewDto> getReviewByDoctor(@PathVariable long doctorId) {
        List<Review> reviews = reviewService.retrieveAllByDoctorId(doctorId);
        return reviewMapper.entityToDto(reviews);
    }

}
