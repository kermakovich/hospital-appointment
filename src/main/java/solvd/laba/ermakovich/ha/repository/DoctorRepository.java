package solvd.laba.ermakovich.ha.repository;

import org.apache.ibatis.annotations.Mapper;
import solvd.laba.ermakovich.ha.domain.SearchDoctorCriteria;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DoctorRepository {

    void save(Doctor doctor);

    boolean isExistById(long id);

    List<Doctor> findAllBySearchCriteria(SearchDoctorCriteria searchDoctorCriteria);

    Optional<Doctor> findById(Long id);

}
