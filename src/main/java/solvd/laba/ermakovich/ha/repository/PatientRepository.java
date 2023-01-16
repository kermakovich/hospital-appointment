package solvd.laba.ermakovich.ha.repository;

import solvd.laba.ermakovich.ha.domain.Patient;

import java.util.Optional;

public interface PatientRepository {

    void save(Patient patient);

    boolean isExistById(long id);

    Optional<Patient> findById(Long id);

}
