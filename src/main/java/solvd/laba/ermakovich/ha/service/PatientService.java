package solvd.laba.ermakovich.ha.service;

import solvd.laba.ermakovich.ha.domain.Patient;

public interface PatientService {

    Patient create(Patient patient);

    boolean isExistById(long id);

}
