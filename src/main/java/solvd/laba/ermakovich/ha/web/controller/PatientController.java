package solvd.laba.ermakovich.ha.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import solvd.laba.ermakovich.ha.domain.Patient;
import solvd.laba.ermakovich.ha.service.PatientService;
import solvd.laba.ermakovich.ha.web.dto.PatientDto;
import solvd.laba.ermakovich.ha.web.mapper.PatientMapper;

@RestController
@RequestMapping("api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;


    @PostMapping
    public ResponseEntity<PatientDto> save(@RequestBody PatientDto patientDto) {
        Patient patient = patientMapper.dtoToEntity(patientDto);
        patientService.save(patient);
        PatientDto dto = patientMapper.entityToDto(patient);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
