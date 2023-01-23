package solvd.laba.ermakovich.ha.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import solvd.laba.ermakovich.ha.service.AppointmentService;

@RestController
@RequestMapping("api/v1/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointment", description = "appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PreAuthorize("(hasRole('PATIENT') or hasRole('ADMIN')) and hasAccessForDelApp(#appointmentId)")
    @DeleteMapping("/{appointmentId}")
    @Operation(description = "delete appointment")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description = "appointment id") @PathVariable long appointmentId) {
        appointmentService.delete(appointmentId);
    }

}
