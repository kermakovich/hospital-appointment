package solvd.laba.ermakovich.ha.repository;

import solvd.laba.ermakovich.ha.domain.PatientCard;

public interface PatientCardRepository {

    PatientCard saveByPatientId(long id);

}
