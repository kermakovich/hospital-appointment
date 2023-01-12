package solvd.laba.ermakovich.ha.service;

import solvd.laba.ermakovich.ha.domain.SearchCriteria;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;

import java.util.List;

public interface DoctorService {

    Doctor save(Doctor doctor);

    boolean existsById(long id);

    List<Doctor> getAllBySearchCriteria(SearchCriteria searchCriteriaDto);

}
