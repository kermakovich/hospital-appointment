package solvd.laba.ermakovich.ha.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.service.AppointmentService;
import solvd.laba.ermakovich.ha.web.dto.AppointmentDto;
import solvd.laba.ermakovich.ha.web.dto.PatientCardDto;
import solvd.laba.ermakovich.ha.web.dto.PatientDto;
import solvd.laba.ermakovich.ha.web.mapper.AppointmentMapper;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class AppointmentController {

     private final AppointmentService appointmentService;
     private final AppointmentMapper appointmentMapper;

    @PostMapping("/{patientId}/appointments")
    public AppointmentDto save(@PathVariable long patientId, @Valid @RequestBody AppointmentDto appointmentDto) {
        //TODO ask dto and about url
        appointmentDto.setPatientCardDto(new PatientCardDto());
        appointmentDto.getPatientCardDto().setPatient(new PatientDto());
        appointmentDto.getPatientCardDto().getPatient().setId(patientId);
        Appointment appointment = appointmentMapper.dtoToEntity(appointmentDto);
        appointmentService.save(appointment);
        return appointmentMapper.entityToDto(appointment);
    }

    @GetMapping("/{patientId}/appointments")
    public List<AppointmentDto> getFuture(@PathVariable long patientId) {
        //TODO ask dto and about url
        List<Appointment> futureAppointments = appointmentService.getAllFutureByPatientId(patientId);
        return appointmentMapper.entityToDto(futureAppointments);
    }

    /**
     * Patient can delete appointment only from his appointment`s list.
     * @param appointmentId appointment`s id to be deleted.
     * @return
     */

    @DeleteMapping("/appointments/{appointmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long appointmentId) {
        appointmentService.delete(appointmentId);
    }

}
