package solvd.laba.ermakovich.ha.service;

import solvd.laba.ermakovich.ha.domain.Patient;

public interface PatientService {

    Patient save(Patient patient);

    boolean existsById(long id);
}
