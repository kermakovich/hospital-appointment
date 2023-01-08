package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.domain.Review;
import solvd.laba.ermakovich.ha.domain.exception.*;
import solvd.laba.ermakovich.ha.repository.ReviewRepository;
import solvd.laba.ermakovich.ha.service.AppointmentService;
import solvd.laba.ermakovich.ha.service.DoctorService;
import solvd.laba.ermakovich.ha.service.PatientService;
import solvd.laba.ermakovich.ha.service.ReviewService;

import java.time.LocalDateTime;
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
        boolean isSaved = false;
        long doctorId = review.getDoctor().getId();
        long patientId = review.getPatient().getId();
        if (!doctorService.existsById(doctorId)) {
            throw new DoctorNotFoundException(doctorId);
        }
        if (!patientService.existsById(patientId)) {
            throw new PatientNotFoundException(patientId);
        }
        if (reviewRepository.existsByDoctorIdAndPatientId(doctorId, patientId)) {
                throw new ReviewAlreadyExistsException(doctorId, patientId);
        }
        var appointments = appointmentService.getAllByPatientIdAndDoctorId(review.getPatient().getId(),
                                                                            review.getDoctor().getId());
        for (Appointment appointment : appointments) {
            if (appointment.getStart().isBefore(LocalDateTime.now())) {
                reviewRepository.save(review);
                isSaved = true;
                break;
            }
        }
        if (!isSaved) {
            throw new PatientNotHaveDoneAppointmentException(review.getPatient().getId(), review.getDoctor().getId());
        }
    }

    @Override
    public List<Review> getAllByDoctorId(long doctorId) {
        if (!doctorService.existsById(doctorId)) {
            throw new DoctorNotFoundException(doctorId);
        }
        return reviewRepository.getAllByDoctorId(doctorId);
    }

    @Override
    @Transactional
    public void delete(long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new ReviewNotFoundException(reviewId);
        }
        reviewRepository.delete(reviewId);
    }

    @Override
    @Transactional
    public Review update(Review review) {
        //TODO ask about exception
        if (!reviewRepository.existsById(review.getId())) {
            throw new ReviewNotFoundException(review.getId());
        }
        reviewRepository.update(review);
        return reviewRepository.getById(review.getId())
                .orElseThrow(() -> new ReviewNotFoundException(review.getId()));
    }

    @Override
    public Review getById(long reviewId) {
        return reviewRepository.getById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }
}
