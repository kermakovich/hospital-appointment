package solvd.laba.ermakovich.ha.service;

import solvd.laba.ermakovich.ha.domain.doctor.AvailibleSlots;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;
import solvd.laba.ermakovich.ha.domain.doctor.Specialization;
import solvd.laba.ermakovich.ha.domain.hospital.Department;

import java.time.LocalDate;
import java.util.List;

public interface DoctorService {

    Doctor save(Doctor doctor);

    List<Doctor> getAllByDepartmentAndSpecialization(Department department, Specialization specialization);

    AvailibleSlots getSchedule(long id, LocalDate date);

    boolean existsById(long id);
}
