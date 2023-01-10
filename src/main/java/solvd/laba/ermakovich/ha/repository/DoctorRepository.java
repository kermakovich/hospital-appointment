package solvd.laba.ermakovich.ha.repository;

import solvd.laba.ermakovich.ha.domain.SearchCriteria;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;

import java.util.List;

public interface DoctorRepository {

    void save(Doctor doctor);

    boolean existsById(long id);

    List<Doctor> getAllBySearchCriteria(SearchCriteria searchCriteria);
}
