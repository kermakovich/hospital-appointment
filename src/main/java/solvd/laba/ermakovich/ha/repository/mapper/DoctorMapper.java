package solvd.laba.ermakovich.ha.repository.mapper;

import solvd.laba.ermakovich.ha.domain.doctor.Doctor;
import solvd.laba.ermakovich.ha.domain.doctor.Specialization;
import solvd.laba.ermakovich.ha.domain.hospital.Department;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DoctorMapper {

    public static List<Doctor> mapList(ResultSet rs) {
        List<Doctor> doctors = new ArrayList<>();
        try {
            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(rs.getLong("user_id"));
                doctor.setName(rs.getString("name"));
                doctor.setSurname(rs.getString("surname"));
                doctor.setFatherhood(rs.getString("fatherhood"));
                doctor.setBirthday(rs.getDate("birthday").toLocalDate());
                doctor.setEmail(rs.getString("email"));
                doctor.setPassword(rs.getString("password"));
                doctor.setDepartment(Department.valueOf(rs.getString("department").toUpperCase()));
                doctor.setSpecialization(Specialization.valueOf(rs.getString("specialization").toUpperCase()));
                doctors.add(doctor);
            }
            return doctors;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Doctor map(ResultSet rs) {
        try {
            Doctor doctor = new Doctor();
            doctor.setId(rs.getLong("doctor_id"));
            doctor.setName(rs.getString("doctor_name"));
            doctor.setSurname(rs.getString("doctor_surname"));
            doctor.setFatherhood(rs.getString("doctor_fatherhood"));
            doctor.setBirthday(rs.getDate("doctor_birthday").toLocalDate());
            doctor.setEmail(rs.getString("doctor_email"));
            doctor.setPassword(rs.getString("doctor_password"));
            doctor.setDepartment(Department.valueOf(rs.getString("doctor_department").toUpperCase()));
            doctor.setSpecialization(Specialization.valueOf(rs.getString("doctor_specialization").toUpperCase()));
            return doctor;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
