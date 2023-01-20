package solvd.laba.ermakovich.ha.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import solvd.laba.ermakovich.ha.domain.Review;
import solvd.laba.ermakovich.ha.service.ReviewService;
import solvd.laba.ermakovich.ha.web.dto.ReviewDto;
import solvd.laba.ermakovich.ha.web.dto.group.onCreateReview;
import solvd.laba.ermakovich.ha.web.mapper.ReviewMapper;

@RestController
@RequestMapping("api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDto create(@RequestBody @Validated(onCreateReview.class) ReviewDto reviewDto) {
        Review review = reviewMapper.dtoToEntity(reviewDto);
        reviewService.create(review);
        return reviewMapper.entityToDto(review);
    }

    @PreAuthorize("(hasRole('PATIENT') or hasRole('ADMIN')) and hasAccessForReview(#reviewId)")
    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long reviewId) {
        reviewService.delete(reviewId);
    }

    @PreAuthorize("(hasRole('PATIENT') or hasRole('ADMIN')) and hasAccessForReview(#reviewId)")
    @PutMapping("/{reviewId}")
    public ReviewDto update(@RequestBody @Valid ReviewDto reviewDto, @PathVariable long reviewId) {
        Review review = reviewMapper.dtoToEntity(reviewDto);
        review = reviewService.update(reviewId, review);
        return reviewMapper.entityToDto(review);
    }

    @PreAuthorize("(hasRole('PATIENT') or hasRole('ADMIN')) and hasAccessForReview(#reviewId)")
    @GetMapping("/{reviewId}")
    public ReviewDto get(@PathVariable long reviewId) {
        Review review = reviewService.retrieveById(reviewId);
        return reviewMapper.entityToDto(review);
    }

}
