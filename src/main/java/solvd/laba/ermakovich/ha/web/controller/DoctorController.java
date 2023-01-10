package solvd.laba.ermakovich.ha.web.controller;

import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import solvd.laba.ermakovich.ha.domain.doctor.AvailibleSlots;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;
import solvd.laba.ermakovich.ha.service.DoctorService;
import solvd.laba.ermakovich.ha.web.dto.AvailableSlotsDto;
import solvd.laba.ermakovich.ha.web.dto.DoctorDto;
import solvd.laba.ermakovich.ha.domain.SearchCriteria;
import solvd.laba.ermakovich.ha.web.dto.group.onCreate;
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
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDto save(@Validated({onCreate.class, Default.class})
                          @RequestBody DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.dtoToEntity(doctorDto);
        doctorService.save(doctor);
        return doctorMapper.entityToDto(doctor);
    }


    @GetMapping
    public List<DoctorDto> getAll(SearchCriteria searchCriteriaDto) {
        List<Doctor> doctors = doctorService.getAllBySearchCriteria(searchCriteriaDto);
        return doctorMapper.entityToDto(doctors);
    }


    @GetMapping("/{id}/schedule")
    public AvailableSlotsDto getAvailableSlots(@PathVariable long id, @RequestParam
                                                                        @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                                        LocalDate date) {
        AvailibleSlots availableSlots = doctorService.getSchedule(id, date);
        return availibleSlotsMapper.entityToDto(availableSlots);
    }
}
