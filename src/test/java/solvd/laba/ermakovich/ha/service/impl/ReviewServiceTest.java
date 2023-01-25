package solvd.laba.ermakovich.ha.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import solvd.laba.ermakovich.ha.domain.Patient;
import solvd.laba.ermakovich.ha.domain.Review;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;
import solvd.laba.ermakovich.ha.domain.exception.IllegalOperationException;
import solvd.laba.ermakovich.ha.domain.exception.ResourceAlreadyExistsException;
import solvd.laba.ermakovich.ha.domain.exception.ResourceDoesNotExistException;
import solvd.laba.ermakovich.ha.repository.ReviewRepository;
import solvd.laba.ermakovich.ha.service.AppointmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    void verifyCreateSuccessfulTest() {
        Review expectedReview = getReview();
        given(reviewRepository.isExistByDoctorIdAndPatientId(anyLong(), anyLong())).willReturn(false);
        given(appointmentService.isExistPastByPatientIdAndDoctorId(anyLong(), anyLong())).willReturn(true);

        Review actualReview = reviewService.create(expectedReview);

        assertEquals(expectedReview, actualReview);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void verifyCreateThrowsResourceAlreadyExistsExceptionTest() {
        Review expectedReview = getReview();
        given(reviewRepository.isExistByDoctorIdAndPatientId(anyLong(), anyLong())).willReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> reviewService.create(expectedReview),
                "Exception wasn`t thrown");
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void verifyCreateThrowsIllegalOperationExceptionTest() {
        Review expectedReview = getReview();
        given(reviewRepository.isExistByDoctorIdAndPatientId(anyLong(), anyLong())).willReturn(false);
        given(appointmentService.isExistPastByPatientIdAndDoctorId(anyLong(), anyLong())).willReturn(false);

        assertThrows(IllegalOperationException.class, () -> reviewService.create(expectedReview),
                "Exception wasn`t thrown");
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void verifyRetrieveAllByDoctorIdTest() {
        final List<Review> expectedReviews = getReviewList();
        final long doctorId = 1L;
        given(reviewRepository.findAllByDoctorId(anyLong())).willReturn(expectedReviews);

        final List<Review> actualReviews = reviewService.retrieveAllByDoctorId(doctorId);

        assertEquals(expectedReviews, actualReviews);
        verify(reviewRepository, times(1)).findAllByDoctorId(anyLong());
    }

    @Test
    void verifyDeleteTest() {
        final long reviewId = 1L;
        reviewService.delete(reviewId);

        verify(reviewRepository, times(1)).delete(anyLong());
    }

    @Test
    void verifyUpdateTest() {
        final long reviewId = 1L;
        Review oldReview = getReview();
        Review newReview = getReview();
        newReview.setDescription("new description");
        given(reviewRepository.findById(anyLong())).willReturn(Optional.of(oldReview));

        Review updatedReview = reviewService.update(reviewId, newReview);

        assertNotNull(updatedReview, "review is null");
        assertEquals(updatedReview.getDescription(), newReview.getDescription());
        verify(reviewRepository, times(1)).update(any(Review.class));
    }

    @Test
    void verifyRetrieveByIdSuccessfulTest() {
        final long reviewId = 1L;
        given(reviewRepository.findById(anyLong())).willReturn(Optional.of(getReview()));

        Review actualReview = reviewService.retrieveById(reviewId);

        assertNotNull(actualReview, "review is null");
        verify(reviewRepository, times(1)).findById(anyLong());
    }

    @Test
    void verifyRetrieveByIdThrowsResourceDoesNotExistExceptionTest() {
        final long reviewId = 1L;
        given(reviewRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(ResourceDoesNotExistException.class, () -> reviewService.retrieveById(reviewId));
        verify(reviewRepository, times(1)).findById(anyLong());
    }

    private Review getReview() {
        Review review = new Review();
        review.setDescription("description");
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        Patient patient = new Patient();
        patient.setId(1L);
        review.setDoctor(doctor);
        review.setPatient(patient);
        return review;
    }

    private List<Review> getReviewList() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(getReview());
        Review review = getReview();
        review.getPatient().setId(2L);
        reviews.add(review);
        return reviews;
    }

}