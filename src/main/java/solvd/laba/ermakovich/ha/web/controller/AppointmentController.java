package solvd.laba.ermakovich.ha.web.controller;

import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.domain.SearchAppointmentCriteria;
import solvd.laba.ermakovich.ha.service.AppointmentService;
import solvd.laba.ermakovich.ha.web.dto.AppointmentDto;
import solvd.laba.ermakovich.ha.web.dto.SearchAppointmentCriteriaDto;
import solvd.laba.ermakovich.ha.web.dto.group.onCreateAppointment;
import solvd.laba.ermakovich.ha.web.mapper.AppointmentMapper;
import solvd.laba.ermakovich.ha.web.mapper.SearchAppointmentCriteriaMapper;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class AppointmentController {

     private final AppointmentService appointmentService;
     private final AppointmentMapper appointmentMapper;
     private final SearchAppointmentCriteriaMapper searchCriteriaMapper;

    @PostMapping("/patients/{patientId}/appointments")
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDto save(@PathVariable long patientId,
                                               @Validated({onCreateAppointment.class, Default.class})
                                               @RequestBody AppointmentDto appointmentDto) {
        Appointment appointment = appointmentMapper.dtoToEntity(appointmentDto);
        appointmentService.save(patientId, appointment);
        appointmentDto = appointmentMapper.entityToDto(appointment);
        return appointmentDto;
    }

    @GetMapping("/patients/{patientId}/appointments")
    public List<AppointmentDto> getByPatientIdAndCriteria(@PathVariable long patientId, SearchAppointmentCriteriaDto criteriaDto) {
        SearchAppointmentCriteria criteria = searchCriteriaMapper.dtoToEntity(criteriaDto);
        List<Appointment> appointments = appointmentService.getAllByPatientIdAndCriteria(patientId, criteria);
        return appointmentMapper.entityToDto(appointments);
    }

    @GetMapping("/doctors/{doctorId}/appointments")
    public List<AppointmentDto> getByDoctorAndCriteria(@PathVariable long doctorId, SearchAppointmentCriteriaDto criteriaDto) {
        SearchAppointmentCriteria criteria = searchCriteriaMapper.dtoToEntity(criteriaDto);
        List<Appointment> appointments = appointmentService.getAllByDoctorIdAndCriteria(doctorId, criteria);
        return appointmentMapper.entityToDto(appointments);
    }

    @DeleteMapping("/appointments/{appointmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long appointmentId) {
        appointmentService.delete(appointmentId);
    }

}
