package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.Review;
import solvd.laba.ermakovich.ha.domain.exception.IllegalOperationException;
import solvd.laba.ermakovich.ha.domain.exception.ResourceAlreadyExistsException;
import solvd.laba.ermakovich.ha.domain.exception.ResourceDoesNotExistException;
import solvd.laba.ermakovich.ha.repository.ReviewRepository;
import solvd.laba.ermakovich.ha.service.AppointmentService;
import solvd.laba.ermakovich.ha.service.ReviewService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final AppointmentService appointmentService;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public void create(Review review) {
        long doctorId = review.getDoctor().getId();
        long patientId = review.getPatient().getId();

        if (reviewRepository.isExistByDoctorIdAndPatientId(doctorId, patientId)) {
                throw new ResourceAlreadyExistsException("Review with patient (id: " + patientId + ") and doctor (id: " + doctorId + ") already exists");
        }
        boolean patientHasPastAppointment = appointmentService.isExistPastByPatientIdAndDoctorId(
                                                                            patientId,
                                                                            doctorId);
        if (patientHasPastAppointment) {
            reviewRepository.save(review);
        } else {
            throw new IllegalOperationException("Patient (id : " + patientId + " ) has not been treated with doctor (id: " + doctorId + " )");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Review> retrieveAllByDoctorId(long doctorId) {
        return reviewRepository.findAllByDoctorId(doctorId);
    }

    @Override
    @Transactional
    public void delete(long reviewId) {
        reviewRepository.delete(reviewId);
    }

    @Override
    @Transactional
    public Review update(long reviewId, Review review) {
        Review oldReview = retrieveById(reviewId);
        oldReview.setDescription(review.getDescription());
        reviewRepository.update(oldReview);
        return oldReview;
    }

    @Override
    @Transactional(readOnly = true)
    public Review retrieveById(long reviewId) {
        return reviewRepository.findById(reviewId)
                        .orElseThrow(() -> new ResourceDoesNotExistException("review with id: " + reviewId + "doesn`t exist"));
    }

}
