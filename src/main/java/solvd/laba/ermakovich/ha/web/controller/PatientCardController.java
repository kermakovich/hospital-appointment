package solvd.laba.ermakovich.ha.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import solvd.laba.ermakovich.ha.domain.card.PatientCard;
import solvd.laba.ermakovich.ha.service.PatientCardService;
import solvd.laba.ermakovich.ha.web.dto.PatientCardDto;
import solvd.laba.ermakovich.ha.web.mapper.PatientCardMapper;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class PatientCardController {

    private final PatientCardService patientCardService;
    private final PatientCardMapper patientCardMapper;

    @GetMapping("/patients/{patientId}/card")
    public PatientCardDto get(@RequestParam long patientId) {
        PatientCard card = patientCardService.get(patientId);
        return patientCardMapper.entityToDto(card);
    }



}
