package solvd.laba.ermakovich.ha.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;
import solvd.laba.ermakovich.ha.domain.doctor.Specialization;
import solvd.laba.ermakovich.ha.domain.hospital.Department;
import solvd.laba.ermakovich.ha.repository.mapper.DoctorMapper;
import solvd.laba.ermakovich.ha.repository.DoctorRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DoctorRepositoryImpl implements DoctorRepository {
    private static final String SAVE = "INSERT INTO doctors (user_id, department, specialization) VALUES (?,?,?)";
    private static final String SELECT_BY_DEPARTMENT = """
            SELECT user_id, name, surname, fatherhood, birthday, email, password, department, specialization 
            FROM doctors
            LEFT JOIN user_info on user_id = id WHERE department = ?""";

    private final DataSource dataSource;

    @Override
    public void save(Doctor doctor) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, doctor.getId());
            ps.setString(2, doctor.getDepartment().toString());
            ps.setString(3, doctor.getSpecialization().toString());
            ps.execute();

            try(ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    doctor.setId(rs.getLong(1));
                }
            }} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Doctor> getAllByDepartmentAndSpecialization(Department department, Specialization specialization) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_DEPARTMENT)) {
            ps.setString(1, department.toString());

            try (ResultSet rs = ps.executeQuery()) {
                return DoctorMapper.mapList(rs);
            }} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
