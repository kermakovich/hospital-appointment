package solvd.laba.ermakovich.ha.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.service.AppointmentService;
import solvd.laba.ermakovich.ha.web.dto.AppointmentDto;
import solvd.laba.ermakovich.ha.web.dto.PatientCardDto;
import solvd.laba.ermakovich.ha.web.dto.PatientDto;
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
    public ResponseEntity<AppointmentDto> save(@PathVariable long patientId,
                                               @Validated(onCreateAppointment.class)
                                               @RequestBody AppointmentDto appointmentDto) {
        //TODO ask dto and about url
        appointmentDto.setPatientCardDto(new PatientCardDto());
        appointmentDto.getPatientCardDto().setPatient(new PatientDto());
        appointmentDto.getPatientCardDto().getPatient().setId(patientId);
        Appointment appointment = appointmentMapper.dtoToEntity(appointmentDto);
        appointmentService.save(appointment);
        appointmentDto = appointmentMapper.entityToDto(appointment);
        return new ResponseEntity<>(appointmentDto, HttpStatus.CREATED);
    }


    @GetMapping("/patients/{patientId}/appointments")
    public ResponseEntity<List<AppointmentDto>> getFutureByPatient(@PathVariable long patientId) {
        //TODO ask dto and about url
        List<Appointment> futureAppointments = appointmentService.getAllFutureByPatientId(patientId);
        List<AppointmentDto> appointmentDtoList = appointmentMapper.entityToDto(futureAppointments);
        return new ResponseEntity<>(appointmentDtoList, HttpStatus.OK);
    }

    @GetMapping("/doctors/{doctorId}/appointments")
    public ResponseEntity<List<AppointmentDto>> getFutureByDoctor(@PathVariable long doctorId) {
        //TODO ask dto and about url
        List<Appointment> futureAppointments = appointmentService.getAllFutureByDoctorId(doctorId);
        List<AppointmentDto> appointmentDtoList = appointmentMapper.entityToDto(futureAppointments);
        return new ResponseEntity<>(appointmentDtoList, HttpStatus.OK);
    }

    /**
     * Patient can delete appointment only from his appointment`s list.
     * @param appointmentId appointment`s id to be deleted.
     */
    @DeleteMapping("/appointments/{appointmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long appointmentId) {
        appointmentService.delete(appointmentId);
    }

}
