package solvd.laba.ermakovich.ha.web.controller;

import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.service.AppointmentService;
import solvd.laba.ermakovich.ha.web.dto.AppointmentDto;
import solvd.laba.ermakovich.ha.web.dto.group.onCreateAppointment;
import solvd.laba.ermakovich.ha.web.mapper.AppointmentMapper;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class AppointmentController {

     private final AppointmentService appointmentService;
     private final AppointmentMapper appointmentMapper;

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
    public List<AppointmentDto> getFutureByPatient(@PathVariable long patientId) {
        List<Appointment> futureAppointments = appointmentService.getAllFutureByPatientId(patientId);
        return appointmentMapper.entityToDto(futureAppointments);
    }


    @GetMapping("/doctors/{doctorId}/appointments")
    public List<AppointmentDto> getFutureByDoctor(@PathVariable long doctorId) {
        List<Appointment> futureAppointments = appointmentService.getAllFutureByDoctorId(doctorId);
        return appointmentMapper.entityToDto(futureAppointments);
    }

    /**
     * Patient can delete appointment only from his appointment`s list.(security later)
     * @param appointmentId appointment`s id to be deleted.
     */
    @DeleteMapping("/appointments/{appointmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long appointmentId) {
        appointmentService.delete(appointmentId);
    }
}
