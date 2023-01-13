package solvd.laba.ermakovich.ha.service;

import solvd.laba.ermakovich.ha.domain.SearchDoctorCriteria;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;

import java.util.List;

public interface DoctorService {

    Doctor save(Doctor doctor);

    boolean isExistById(long id);

    List<Doctor> getAllBySearchCriteria(SearchDoctorCriteria searchDoctorCriteriaDto);

}
