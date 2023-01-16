package solvd.laba.ermakovich.ha.repository;

import org.apache.ibatis.annotations.Param;
import solvd.laba.ermakovich.ha.domain.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {

    void save(Review review);

    boolean isExistByDoctorIdAndPatientId(@Param("doctorId") long doctorId, @Param("patientId") long patientId);

    boolean isExistById(long reviewId);

    void delete(long reviewId);

    void update(Review review);

    Optional<Review> findById(long id);

    List<Review> findAllByDoctorId(long doctorId);

}
