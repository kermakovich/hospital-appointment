package solvd.laba.ermakovich.ha.repository;

import solvd.laba.ermakovich.ha.domain.doctor.Doctor;
import solvd.laba.ermakovich.ha.domain.doctor.Specialization;
import solvd.laba.ermakovich.ha.domain.hospital.Department;

import java.util.List;

public interface DoctorRepository {

    void save(Doctor doctor);
    List<Doctor> getAllByDepartmentAndSpecialization(Department department, Specialization specialization);

    boolean existsById(long id);
}
