package solvd.laba.ermakovich.ha.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.Patient;
import solvd.laba.ermakovich.ha.domain.PatientCard;
import solvd.laba.ermakovich.ha.repository.PatientCardRepository;
import solvd.laba.ermakovich.ha.service.PatientCardService;

import java.time.LocalDate;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PatientCardServiceImpl implements PatientCardService {

    private final PatientCardRepository patientCardRepository;

    @Override
    @Transactional
    public PatientCard createByPatientId(long id) {
        PatientCard card = new PatientCard(id, new Patient(), UUID.randomUUID(), LocalDate.now());
        patientCardRepository.save(card);
        return card;
    }

}
