package solvd.laba.ermakovich.ha.repository;

import solvd.laba.ermakovich.ha.domain.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {

    void save(Review review);

    boolean existsByDoctorIdAndPatientId(long doctorId, long patientId);

    boolean existsById(long reviewId);

    void delete(long reviewId);

    void update(Review review);

    Optional<Review> getById(long id);

    List<Review> getAllByDoctorId(long doctorId);
}
