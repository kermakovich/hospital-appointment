package solvd.laba.ermakovich.ha.web.controller;

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

@RestController
@RequestMapping("api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;
    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;
    private final SearchAppointmentCriteriaMapper searchAppointmentCriteriaMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDto create(@Validated({onCreate.class, Default.class}) @RequestBody PatientDto patientDto) {
        Patient patient = patientMapper.dtoToEntity(patientDto);
        patientService.create(patient);
        return patientMapper.entityToDto(patient);
    }

    //todo expr that patient post app for himself
//    (hasRole('PATIENT') or hasRole('ADMIN')) and
    @PreAuthorize("hasPatientAccess(#patientId)")
    @PostMapping("/{patientId}/appointments")
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDto createAppointment(@PathVariable Long patientId,
                               @Validated({onCreateAppointment.class, Default.class})
                               @RequestBody AppointmentDto appointmentDto) {
        Appointment appointment = appointmentMapper.dtoToEntity(appointmentDto);
        appointmentService.save(patientId, appointment);
        appointmentDto = appointmentMapper.entityToDto(appointment);
        return appointmentDto;
    }

    //todo expr that patient get app for himself
    @PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN')")
    @GetMapping("/{patientId}/appointments")
    public List<AppointmentDto> getAppointmentByPatientIdAndCriteria(@PathVariable long patientId, SearchAppointmentCriteriaDto criteriaDto) {
        SearchAppointmentCriteria criteria = searchAppointmentCriteriaMapper.dtoToEntity(criteriaDto);
        List<Appointment> appointments = appointmentService.retrieveAllByPatientIdAndCriteria(patientId, criteria);
        return appointmentMapper.entityToDto(appointments);
    }

}
