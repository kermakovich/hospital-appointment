package solvd.laba.ermakovich.ha.service;

import solvd.laba.ermakovich.ha.domain.Patient;

public interface PatientService {

    String entityName = "patient";

    Patient save(Patient patient);

    boolean existsById(long id);
}
