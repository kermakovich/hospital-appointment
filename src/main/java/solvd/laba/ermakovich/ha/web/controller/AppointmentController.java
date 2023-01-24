package solvd.laba.ermakovich.ha.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import solvd.laba.ermakovich.ha.service.AppointmentService;

import static solvd.laba.ermakovich.ha.web.security.SecurityConfig.SECURITY_SCHEME_NAME;

@RestController
@RequestMapping("api/v1/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointment", description = "appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PreAuthorize("(hasRole('PATIENT') or hasRole('ADMIN')) and hasAccessForDelApp(#appointmentId)")
    @DeleteMapping("/{appointmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "deletes appointment")
    @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    public void delete(@Parameter(description = "appointment id") @PathVariable long appointmentId) {
        appointmentService.delete(appointmentId);
    }

}
