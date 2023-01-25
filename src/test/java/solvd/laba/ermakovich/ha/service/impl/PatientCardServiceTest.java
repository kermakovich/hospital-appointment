package solvd.laba.ermakovich.ha.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import solvd.laba.ermakovich.ha.domain.PatientCard;
import solvd.laba.ermakovich.ha.repository.PatientCardRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PatientCardServiceTest {

    @Mock
    private PatientCardRepository patientCardRepository;

    @InjectMocks
    private PatientCardServiceImpl patientCardService;

    @Test
    void verifyCreateByPatientIdTest() {
        PatientCard patientCard = patientCardService.createByPatientId(1L);

        assertNotNull(patientCard.getNumber(), "card number is null");
        assertNotNull(patientCard.getRegistryDate(), "registry date is null");
        verify(patientCardRepository, times(1)).save(any(PatientCard.class));
    }

}