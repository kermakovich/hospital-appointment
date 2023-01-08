package solvd.laba.ermakovich.ha.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvd.laba.ermakovich.ha.domain.doctor.AvailibleSlots;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;
import solvd.laba.ermakovich.ha.domain.doctor.Specialization;
import solvd.laba.ermakovich.ha.domain.hospital.Department;
import solvd.laba.ermakovich.ha.service.DoctorService;
import solvd.laba.ermakovich.ha.web.dto.AvailibleSlotsDto;
import solvd.laba.ermakovich.ha.web.dto.DoctorDto;
import solvd.laba.ermakovich.ha.web.mapper.AvailibleSlotsMapper;
import solvd.laba.ermakovich.ha.web.mapper.DoctorMapper;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;
    private final AvailibleSlotsMapper availibleSlotsMapper;

    @PostMapping
    public ResponseEntity<DoctorDto> save(@Valid @RequestBody DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.dtoToEntity(doctorDto);
        doctorService.save(doctor);
        DoctorDto dto = doctorMapper.entityToDto(doctor);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/{department}/{specialization}")
    //TODO ask url
    public ResponseEntity<List<DoctorDto>> getAll(@PathVariable Department department,
                                                  @PathVariable Specialization specialization) {
        List<Doctor> doctors = doctorService.getAllByDepartmentAndSpecialization(department, specialization);
        List<DoctorDto> doctorDtos = doctorMapper.entityToDto(doctors);
        return new ResponseEntity<>(doctorDtos, HttpStatus.OK);
    }


    @GetMapping("/{id}/schedule")
    public ResponseEntity<AvailibleSlotsDto> getAvailibleSlots(@PathVariable long id,
                                                               @RequestParam("date")
                                                               @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        //TODO ask if its right controller
        AvailibleSlots availibleSlots = doctorService.getSchedule(id, date);
        AvailibleSlotsDto slots = availibleSlotsMapper.entityToDto(availibleSlots);
        return new ResponseEntity<>(slots, HttpStatus.OK);
    }
}
