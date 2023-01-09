package solvd.laba.ermakovich.ha.repository.mapper;

import lombok.SneakyThrows;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;
import solvd.laba.ermakovich.ha.domain.doctor.Specialization;
import solvd.laba.ermakovich.ha.domain.hospital.Department;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class DoctorMapper {

    @SneakyThrows
    public static List<Doctor> mapList(ResultSet rs) {
        List<Doctor> doctors = new ArrayList<>();
        while (rs.next()) {
            doctors.add(map(rs));
        }
        return doctors;
    }

    @SneakyThrows
    public static Doctor map(ResultSet rs) {
        Doctor doctor = new Doctor();
        doctor.setId(rs.getLong("doctor_id"));
        doctor.setName(rs.getString("doctor_name"));
        doctor.setSurname(rs.getString("doctor_surname"));
        doctor.setFatherhood(rs.getString("doctor_fatherhood"));
        doctor.setBirthday(rs.getDate("doctor_birthday").toLocalDate());
        doctor.setEmail(rs.getString("doctor_email"));
//        doctor.setPassword(rs.getString("doctor_password"));
        doctor.setDepartment(Department.valueOf(rs.getString("doctor_department").toUpperCase()));
        doctor.setSpecialization(Specialization.valueOf(rs.getString("doctor_specialization").toUpperCase()));
        return doctor;
    }

    @SneakyThrows
    public static Doctor mapId(ResultSet rs) {
        Doctor doctor = new Doctor();
        doctor.setId(rs.getLong("doctor_id"));
        return doctor;
    }
}
