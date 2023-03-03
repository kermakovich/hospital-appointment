package solvd.laba.ermakovich.ha.web.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import static solvd.laba.ermakovich.ha.web.security.SecurityConfig.SECURITY_SCHEME_NAME;

@RestController
@RequestMapping("api/v1/doctors")
@RequiredArgsConstructor
@Tag(name = "Doctor", description = "doctor")
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
    @Operation(summary = "adds doctor to hospital")
    @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    @CircuitBreaker(name = "doctorService", fallbackMethod = "throwError")
    public DoctorDto create(@Validated({onCreate.class, Default.class}) @RequestBody DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.dtoToEntity(doctorDto);
        doctorService.create(doctor);
        return doctorMapper.entityToDto(doctor);
    }

    private DoctorDto throwError(IllegalStateException e) {
        throw new RuntimeException ("please, try later");
    }

    @GetMapping
    @Operation(summary = "gets doctors by search criteria")
    public List<DoctorDto> getAll(SearchCriteriaDto searchCriteriaDto) {
        SearchDoctorCriteria criteria = searchCriteriaMapper.dtoToEntity(searchCriteriaDto);
        List<Doctor> doctors = doctorService.retrieveAllBySearchCriteria(criteria);
        return doctorMapper.entityToDto(doctors);
    }


    @GetMapping("/{id}/schedule")
    @Operation(summary = "gets available doctor`s time slots")
    public AvailableSlotsDto getAvailableSlots(@Parameter(description = "doctor id") @PathVariable long id,
                                               @Parameter(description = "date for searching slots",  example = "10-08-2023") @RequestParam LocalDate date) {
        AvailableSlots availableSlots = timeSlotService.retrieveSchedule(id, date);
        return availableSlotsMapper.entityToDto(availableSlots);
    }

    @PreAuthorize("(hasRole('DOCTOR') or hasRole('ADMIN')) and hasAccess(#doctorId)")
    @GetMapping("/{doctorId}/appointments")
    @Operation(summary = "gets doctor`s appointments")
    @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    public List<AppointmentDto> getAppointmentByDoctorAndCriteria(@Parameter(description = "doctor id") @PathVariable long doctorId,
                                                                   SearchAppointmentCriteriaDto criteriaDto) {
        SearchAppointmentCriteria criteria = searchAppointmentCriteriaMapper.dtoToEntity(criteriaDto);
        List<Appointment> appointments = appointmentService.retrieveAllByDoctorIdAndCriteria(doctorId, criteria);
        return appointmentMapper.entityToDto(appointments);
    }

    @GetMapping("/{doctorId}/reviews")
    @Operation(summary = "gets doctor`s reviews")
    public List<ReviewDto> getReviewByDoctor(@Parameter(description = "doctor id") @PathVariable long doctorId) {
        List<Review> reviews = reviewService.retrieveAllByDoctorId(doctorId);
        return reviewMapper.entityToDto(reviews);
    }

}
