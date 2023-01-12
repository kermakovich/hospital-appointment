package solvd.laba.ermakovich.ha.service;

import solvd.laba.ermakovich.ha.domain.SearchCriteria;
import solvd.laba.ermakovich.ha.domain.doctor.AvailableSlots;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;

import java.time.LocalDate;
import java.util.List;

public interface DoctorService {

    Doctor save(Doctor doctor);

    AvailableSlots getSchedule(long id, LocalDate date);

    boolean existsById(long id);

    List<Doctor> getAllBySearchCriteria(SearchCriteria searchCriteriaDto);

}
