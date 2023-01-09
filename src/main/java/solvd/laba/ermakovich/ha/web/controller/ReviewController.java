package solvd.laba.ermakovich.ha.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import solvd.laba.ermakovich.ha.domain.Review;
import solvd.laba.ermakovich.ha.service.ReviewService;
import solvd.laba.ermakovich.ha.web.dto.ReviewDto;
import solvd.laba.ermakovich.ha.web.dto.group.onCreateReview;
import solvd.laba.ermakovich.ha.web.mapper.ReviewMapper;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @PostMapping("/reviews")
    public ResponseEntity<ReviewDto> save(@RequestBody @Validated(onCreateReview.class) ReviewDto reviewDto) {
        Review review = reviewMapper.dtoToEntity(reviewDto);
        reviewService.save(review);
        reviewDto = reviewMapper.entityToDto(review);
        return new ResponseEntity<>(reviewDto, HttpStatus.CREATED);
    }

    @GetMapping("/{doctorId}/reviews")
    public ResponseEntity<List<ReviewDto>> getByDoctor(@PathVariable long doctorId) {
        List<Review> reviews = reviewService.getAllByDoctorId(doctorId);
        List<ReviewDto> dtoList = reviewMapper.entityToDto(reviews);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @DeleteMapping("/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long reviewId) {
        reviewService.delete(reviewId);
    }

    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> update(@RequestBody @Valid ReviewDto reviewDto, @PathVariable long reviewId) {
        Review review = reviewMapper.dtoToEntity(reviewDto);
        review.setId(reviewId);
        review = reviewService.update(review);
        reviewDto = reviewMapper.entityToDto(review);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> getOne(@PathVariable long reviewId) {
        Review review = reviewService.getById(reviewId);
        ReviewDto dto = reviewMapper.entityToDto(review);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
