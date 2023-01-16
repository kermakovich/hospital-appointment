package solvd.laba.ermakovich.ha.repository.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import solvd.laba.ermakovich.ha.domain.SearchDoctorCriteria;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;
import solvd.laba.ermakovich.ha.repository.DoctorRepository;
import solvd.laba.ermakovich.ha.repository.DataSourceConfig;
import solvd.laba.ermakovich.ha.repository.jdbc.mapper.DoctorMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "persistence", name = "type", havingValue = "jdbc")
public class DoctorRepositoryImpl implements DoctorRepository {

    private static final String SAVE = "INSERT INTO doctors (user_id, department, specialization, cabinet) VALUES (?,?,?,?)";
    private static final String SELECT_BY_SEARCH_CRITERIA = """
            SELECT user_id as "doctor_id", name as "doctor_name", 
            surname as "doctor_surname", fatherhood as "doctor_fatherhood", 
            birthday as "doctor_birthday", email as "doctor_email", 
            department as "doctor_department", specialization as "doctor_specialization",
            cabinet as "doctor_cabinet"
            FROM doctors
            LEFT JOIN user_info on user_id = id 
            """;
    private static final String CHECK_IF_EXISTS_BY_ID = "SELECT user_id FROM doctors WHERE user_id = ?";
    private final DataSourceConfig dataSource;

    @Override
    @SneakyThrows
    public void save(Doctor doctor) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, doctor.getId());
            ps.setString(2, doctor.getDepartment().toString());
            ps.setString(3, doctor.getSpecialization().toString());
            ps.setObject(4, doctor.getCabinet());
            ps.execute();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    doctor.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    @SneakyThrows
    public boolean isExistById(long id) {
        Connection con = dataSource.getConnection();
        try (PreparedStatement ps = con.prepareStatement(CHECK_IF_EXISTS_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    @SneakyThrows
    public List<Doctor> findAllBySearchCriteria(SearchDoctorCriteria searchDoctorCriteria) {
        Connection con = dataSource.getConnection();
        try( PreparedStatement ps = con.prepareStatement(SELECT_BY_SEARCH_CRITERIA
                                                            + buildWhereClauseBySearchCriteria(searchDoctorCriteria))) {
            try (ResultSet rs = ps.executeQuery()) {
                return DoctorMapper.mapList(rs);
            }
        }
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        return Optional.empty();
    }

    private String buildWhereClauseBySearchCriteria (SearchDoctorCriteria searchDoctorCriteria) {
        List<String> conditions = new ArrayList<>();
        if (searchDoctorCriteria.getDepartment() != null) {
            conditions.add("department='" + searchDoctorCriteria.getDepartment() + "'");
        }
        if (searchDoctorCriteria.getSpecialization() != null) {
            conditions.add("specialization='" + searchDoctorCriteria.getSpecialization() + "'");
        }
        if (!conditions.isEmpty()) {
            return " WHERE " + String.join(" and ", conditions);
        } else {
            return Strings.EMPTY;
        }
    }

}
