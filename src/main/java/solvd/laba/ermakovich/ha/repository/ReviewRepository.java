package solvd.laba.ermakovich.ha.repository;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import solvd.laba.ermakovich.ha.domain.Review;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ReviewRepository {

    void save(Review review);

    boolean isExistByDoctorIdAndPatientId(@Param("doctorId") long doctorId, @Param("patientId") long patientId);

    boolean isExistById(long reviewId);

    Optional<Review> findById(long id);

    List<Review> findAllByDoctorId(long doctorId);

    void update(Review review);

    void delete(long reviewId);

}
