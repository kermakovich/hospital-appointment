package solvd.laba.ermakovich.ha.repository;

import org.apache.ibatis.annotations.Mapper;
import solvd.laba.ermakovich.ha.domain.Patient;

@Mapper
public interface PatientRepository {

    void save(Patient patient);

    boolean isExistById(long id);

}
