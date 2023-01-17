package solvd.laba.ermakovich.ha.repository;

import org.apache.ibatis.annotations.Mapper;
import solvd.laba.ermakovich.ha.domain.Patient;

import java.util.Optional;

@Mapper
public interface PatientRepository {

    void save(Patient patient);

    boolean isExistById(long id);

    Optional<Patient> findById(Long id);

}
