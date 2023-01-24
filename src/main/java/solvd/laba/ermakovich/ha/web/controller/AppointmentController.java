package solvd.laba.ermakovich.ha.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import solvd.laba.ermakovich.ha.service.AppointmentService;

@RestController
@RequestMapping("api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PreAuthorize("(hasRole('PATIENT') or hasRole('ADMIN')) and hasAccessForDelApp(#appointmentId)")
    @DeleteMapping("/{appointmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long appointmentId) {
        appointmentService.delete(appointmentId);
    }

}
