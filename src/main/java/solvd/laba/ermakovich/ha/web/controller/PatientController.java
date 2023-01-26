package solvd.laba.ermakovich.ha.web.controller;

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
import solvd.laba.ermakovich.ha.domain.Patient;
import solvd.laba.ermakovich.ha.domain.SearchAppointmentCriteria;
import solvd.laba.ermakovich.ha.service.AppointmentService;
import solvd.laba.ermakovich.ha.service.PatientService;
import solvd.laba.ermakovich.ha.web.dto.AppointmentDto;
import solvd.laba.ermakovich.ha.web.dto.PatientDto;
import solvd.laba.ermakovich.ha.web.dto.SearchAppointmentCriteriaDto;
import solvd.laba.ermakovich.ha.web.dto.group.onCreate;
import solvd.laba.ermakovich.ha.web.dto.group.onCreateAppointment;
import solvd.laba.ermakovich.ha.web.mapper.AppointmentMapper;
import solvd.laba.ermakovich.ha.web.mapper.PatientMapper;
import solvd.laba.ermakovich.ha.web.mapper.SearchAppointmentCriteriaMapper;

import java.util.List;

import static solvd.laba.ermakovich.ha.web.security.SecurityConfig.SECURITY_SCHEME_NAME;


@RestController
@RequestMapping("api/v1/patients")
@RequiredArgsConstructor
@Tag(name = "Patient", description = "patient")
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;
    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;
    private final SearchAppointmentCriteriaMapper searchAppointmentCriteriaMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates patient")
    public PatientDto create(@Validated({onCreate.class, Default.class}) @RequestBody PatientDto patientDto) {
        Patient patient = patientMapper.dtoToEntity(patientDto);
        patientService.create(patient);
        return patientMapper.entityToDto(patient);
    }

    @PreAuthorize("(hasRole('PATIENT') or hasRole('ADMIN')) and hasAccess(#patientId)")
    @PostMapping("/{patientId}/appointments")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates appointment by patient")
    @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    public AppointmentDto createAppointment(@Parameter(description = "patient id") @PathVariable Long patientId,
                               @Validated({onCreateAppointment.class, Default.class})
                               @RequestBody AppointmentDto appointmentDto) {
        Appointment appointment = appointmentMapper.dtoToEntity(appointmentDto);
        appointmentService.create(patientId, appointment);
        appointmentDto = appointmentMapper.entityToDto(appointment);
        return appointmentDto;
    }

    @PreAuthorize("(hasRole('PATIENT') or hasRole('ADMIN')) and hasAccess(#patientId)")
    @GetMapping("/{patientId}/appointments")
    @Operation(summary = "gets appointments by patient and criteria")
    @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    public List<AppointmentDto> getAppointmentByPatientIdAndCriteria(@Parameter(description = "patient id") @PathVariable long patientId,
                                                                     SearchAppointmentCriteriaDto criteriaDto) {
        SearchAppointmentCriteria criteria = searchAppointmentCriteriaMapper.dtoToEntity(criteriaDto);
        List<Appointment> appointments = appointmentService.retrieveAllByPatientIdAndCriteria(patientId, criteria);
        return appointmentMapper.entityToDto(appointments);
    }

}
