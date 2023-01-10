package solvd.laba.ermakovich.ha.service;

import solvd.laba.ermakovich.ha.domain.card.PatientCard;

public interface PatientCardService {

    PatientCard saveByPatientId(long id);

    PatientCard get(long patientId);
}
