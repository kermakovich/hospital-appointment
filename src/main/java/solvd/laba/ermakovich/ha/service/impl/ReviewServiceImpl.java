package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.Review;
import solvd.laba.ermakovich.ha.domain.exception.IllegalOperationException;
import solvd.laba.ermakovich.ha.domain.exception.ResourceNotFoundException;
import solvd.laba.ermakovich.ha.domain.exception.EntityAlreadyExistsException;
import solvd.laba.ermakovich.ha.repository.ReviewRepository;
import solvd.laba.ermakovich.ha.service.AppointmentService;
import solvd.laba.ermakovich.ha.service.DoctorService;
import solvd.laba.ermakovich.ha.service.PatientService;
import solvd.laba.ermakovich.ha.service.ReviewService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public void save(Review review) {
        long doctorId = review.getDoctor().getId();
        long patientId = review.getPatient().getId();

        if (reviewRepository.existsByDoctorIdAndPatientId(doctorId, patientId)) {
                throw new EntityAlreadyExistsException("Review with patient (id: " +
                        patientId + ") and doctor (id: " + doctorId + ") already exists");
        }
        boolean patientHasPastAppointment = appointmentService.existsPastByPatientIdAndDoctorId(
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
    public Review update(Review review) {
        Review oldReview = getById(review.getId());
        oldReview.setDescription(review.getDescription());
        reviewRepository.update(oldReview);
        return review;
    }

    @Override
    public Review getById(long reviewId) {
        return reviewRepository.getById(reviewId)
                        .orElseThrow(() -> new ResourceNotFoundException(entityName, reviewId));
    }
}
