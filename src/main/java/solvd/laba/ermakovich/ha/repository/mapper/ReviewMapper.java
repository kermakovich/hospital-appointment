package solvd.laba.ermakovich.ha.repository.mapper;

import solvd.laba.ermakovich.ha.domain.Patient;
import solvd.laba.ermakovich.ha.domain.Review;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ReviewMapper {

    public static List<Review> mapList(ResultSet rs) {
        List<Review> reviews = new ArrayList<>();
        try {
            while (rs.next()) {
                reviews.add(map(rs));
            }
            return reviews;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Review map(ResultSet rs) {
        try {
            Review review = new Review();
            review.setId(rs.getLong("review_id"));
            review.setDescription(rs.getString("review_description"));
            Doctor doctor = DoctorMapper.map(rs);
            review.setDoctor(doctor);
            Patient patient = PatientMapper.map(rs);
            review.setPatient(patient);
            return review;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
