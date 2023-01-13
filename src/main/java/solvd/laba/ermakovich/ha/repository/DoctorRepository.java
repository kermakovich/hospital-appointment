package solvd.laba.ermakovich.ha.repository;

import solvd.laba.ermakovich.ha.domain.SearchDoctorCriteria;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;

import java.util.List;

public interface DoctorRepository {

    void save(Doctor doctor);

    boolean isExistById(long id);

    List<Doctor> findAllBySearchCriteria(SearchDoctorCriteria searchDoctorCriteria);

}
