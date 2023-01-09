package solvd.laba.ermakovich.ha.service;

import solvd.laba.ermakovich.ha.domain.Review;

import java.util.List;

public interface ReviewService {

    String entityName = "review";

    void save(Review review);

    List<Review> getAllByDoctorId(long doctorId);

    void delete(long reviewId);

    Review update(Review review);

    Review getById(long reviewId);
}
