package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.Review;
import solvd.laba.ermakovich.ha.domain.exception.ResourceAlreadyExistsException;
import solvd.laba.ermakovich.ha.domain.exception.IllegalOperationException;
import solvd.laba.ermakovich.ha.domain.exception.ResourceDoesNotExistException;
import solvd.laba.ermakovich.ha.repository.ReviewRepository;
import solvd.laba.ermakovich.ha.service.AppointmentService;
import solvd.laba.ermakovich.ha.service.DoctorService;
import solvd.laba.ermakovich.ha.service.PatientService;
import solvd.laba.ermakovich.ha.service.ReviewService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final AppointmentService appointmentService;
    private final ReviewRepository reviewRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @Override
    @Transactional
    public void save(Review review) {
        long doctorId = review.getDoctor().getId();
        long patientId = review.getPatient().getId();

        if (reviewRepository.existsByDoctorIdAndPatientId(doctorId, patientId)) {
                throw new ResourceAlreadyExistsException("Review with patient (id: " +
                        patientId + ") and doctor (id: " + doctorId + ") already exists");
        }
        boolean patientHasPastAppointment = appointmentService.isExistPastByPatientIdAndDoctorId(
                                                                            patientId,
                                                                            doctorId);
        if (patientHasPastAppointment) {
            reviewRepository.save(review);
        } else {
            throw new IllegalOperationException("Patient (id : " + patientId
                                                + " ) has not been treated with doctor (id: "
                                                + doctorId + " )");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Review> getAllByDoctorId(long doctorId) {
        return reviewRepository.getAllByDoctorId(doctorId);
    }

    @Override
    @Transactional
    public void delete(long reviewId) {
        reviewRepository.delete(reviewId);
    }

    @Override
    @Transactional
    public Review update(long reviewId, Review review) {
        Review oldReview = getById(reviewId);
        oldReview.setDescription(review.getDescription());
        reviewRepository.update(oldReview);
        return oldReview;
    }

    @Override
    @Transactional(readOnly = true)
    public Review getById(long reviewId) {
        return reviewRepository.getById(reviewId)
                        .orElseThrow(() -> new ResourceDoesNotExistException("review", reviewId));
    }

}
