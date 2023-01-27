package solvd.laba.ermakovich.ha.service;

import solvd.laba.ermakovich.ha.domain.Review;

import java.util.List;

public interface ReviewService {

    Review create(Review review);

    List<Review> retrieveAllByDoctorId(long doctorId);

    void delete(long reviewId);

    Review update(long reviewId, Review review);

    Review retrieveById(long reviewId);

}
