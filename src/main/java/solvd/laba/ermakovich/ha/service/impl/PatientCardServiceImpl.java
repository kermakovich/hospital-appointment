package solvd.laba.ermakovich.ha.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.PatientCard;
import solvd.laba.ermakovich.ha.repository.PatientCardRepository;
import solvd.laba.ermakovich.ha.service.PatientCardService;

@Service
@AllArgsConstructor
public class PatientCardServiceImpl implements PatientCardService {

    private final PatientCardRepository patientCardRepository;

    @Override
    @Transactional
    public PatientCard saveByPatientId(long id) {
        return patientCardRepository.saveByPatientId(id);
    }

}
