package solvd.laba.ermakovich.ha.repository;

import solvd.laba.ermakovich.ha.domain.card.PatientCard;

public interface PatientCardRepository {

    PatientCard saveByPatientId(long id);
}
