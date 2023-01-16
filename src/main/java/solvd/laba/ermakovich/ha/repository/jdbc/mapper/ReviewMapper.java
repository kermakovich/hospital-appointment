package solvd.laba.ermakovich.ha.repository.jdbc.mapper;

import lombok.SneakyThrows;
import solvd.laba.ermakovich.ha.domain.Patient;
import solvd.laba.ermakovich.ha.domain.Review;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class ReviewMapper {

    @SneakyThrows
    public static List<Review> mapListForDoctor(ResultSet rs) {
        List<Review> reviews = new ArrayList<>();
        while (rs.next()) {
            reviews.add(mapForDoctor(rs));
        }
        return reviews;
    }

    @SneakyThrows
    public static Review mapForDoctor(ResultSet rs) {
        Review review = new Review();
        review.setId(rs.getLong("review_id"));
        review.setDescription(rs.getString("review_description"));
        review.setDoctor(DoctorMapper.mapId(rs));
        Patient patient = PatientMapper.mapNameAndId(rs);
        review.setPatient(patient);
        return review;
    }

    @SneakyThrows
    public static Review map(ResultSet rs) {
        Review review = new Review();
        review.setId(rs.getLong("review_id"));
        review.setDescription(rs.getString("review_description"));
        review.setDoctor(DoctorMapper.map(rs));
        Patient patient = PatientMapper.mapNameAndId(rs);
        review.setPatient(patient);
        return review;
    }

}
