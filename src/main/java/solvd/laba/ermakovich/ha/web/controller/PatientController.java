package solvd.laba.ermakovich.ha.web.controller;

import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import solvd.laba.ermakovich.ha.domain.Patient;
import solvd.laba.ermakovich.ha.service.PatientService;
import solvd.laba.ermakovich.ha.web.dto.PatientDto;
import solvd.laba.ermakovich.ha.web.dto.group.onCreate;
import solvd.laba.ermakovich.ha.web.mapper.PatientMapper;

@RestController
@RequestMapping("api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDto save(@Validated({onCreate.class, Default.class})
                                               @RequestBody PatientDto patientDto) {
        Patient patient = patientMapper.dtoToEntity(patientDto);
        patientService.save(patient);
        return patientMapper.entityToDto(patient);
    }
}
