package solvd.laba.ermakovich.ha.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;
import solvd.laba.ermakovich.ha.domain.doctor.Specialization;
import solvd.laba.ermakovich.ha.domain.hospital.Department;
import solvd.laba.ermakovich.ha.repository.DoctorRepository;
import solvd.laba.ermakovich.ha.repository.config.DataSourceConfig;
import solvd.laba.ermakovich.ha.repository.mapper.DoctorMapper;

import java.sql.*;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DoctorRepositoryImpl implements DoctorRepository {
    private static final String SAVE = "INSERT INTO doctors (user_id, department, specialization) VALUES (?,?,?)";
    private static final String SELECT_BY_DEPARTMENT_AND_SPECIALIZATION = """
            SELECT user_id as "doctor_id", name as "doctor_name", 
            surname as "doctor_surname", fatherhood as "doctor_fatherhood", 
            birthday as "doctor_birthday", email as "doctor_email", 
            department as "doctor_department", specialization as "doctor_specialization"
            FROM doctors
            LEFT JOIN user_info on user_id = id WHERE department = ? and specialization=?""";
    private static final String CHECK_IF_EXISTS_BY_ID = "SELECT user_id FROM doctors WHERE user_id = ?";

    private final DataSourceConfig dataSource;

    @Override
    public void save(Doctor doctor) {
        try {
            Connection con = dataSource.getConnection();
            try (PreparedStatement ps = con.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, doctor.getId());
                ps.setString(2, doctor.getDepartment().toString());
                ps.setString(3, doctor.getSpecialization().toString());
                ps.execute();

                try(ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        doctor.setId(rs.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Doctor> getAllByDepartmentAndSpecialization(Department department, Specialization specialization) {
        try {
            Connection con = dataSource.getConnection();
            try (PreparedStatement ps = con.prepareStatement(SELECT_BY_DEPARTMENT_AND_SPECIALIZATION)) {
                ps.setString(1, department.toString());
                ps.setString(2, specialization.toString());
                try (ResultSet rs = ps.executeQuery()) {
                    return DoctorMapper.mapList(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(long id) {
        try {
            Connection con = dataSource.getConnection();
            try (PreparedStatement ps = con.prepareStatement(CHECK_IF_EXISTS_BY_ID)) {
                ps.setLong(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
