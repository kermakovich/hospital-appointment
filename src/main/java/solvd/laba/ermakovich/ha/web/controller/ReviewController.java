package solvd.laba.ermakovich.ha.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Review", description = "doctor`s reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @PreAuthorize("(hasRole('PATIENT') or hasRole('ADMIN')) and hasAccess(#reviewDto.patientDto.id)")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "post doctor`s review by patient")
    public ReviewDto create(@RequestBody @Validated(onCreateReview.class) ReviewDto reviewDto) {
        Review review = reviewMapper.dtoToEntity(reviewDto);
        reviewService.create(review);
        return reviewMapper.entityToDto(review);
    }

    @PreAuthorize("(hasRole('PATIENT') or hasRole('ADMIN')) and hasAccessForReview(#reviewId)")
    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "delete review by patient")
    public void delete(@Parameter(description = "id review for delete") @PathVariable long reviewId) {
        reviewService.delete(reviewId);
    }

    @PreAuthorize("(hasRole('PATIENT') or hasRole('ADMIN')) and hasAccessForReview(#reviewId)")
    @PutMapping("/{reviewId}")
    @Operation(description = "update review by patient")
    public ReviewDto update(@RequestBody @Valid ReviewDto reviewDto,
                            @Parameter(description = "id review for update") @PathVariable long reviewId) {
        Review review = reviewMapper.dtoToEntity(reviewDto);
        review = reviewService.update(reviewId, review);
        return reviewMapper.entityToDto(review);
    }

    @PreAuthorize("(hasRole('PATIENT') or hasRole('ADMIN')) and hasAccessForReview(#reviewId)")
    @GetMapping("/{reviewId}")
    @Operation(description = "get review by id")
    public ReviewDto get(@Parameter(description = "id review") @PathVariable long reviewId) {
        Review review = reviewService.retrieveById(reviewId);
        return reviewMapper.entityToDto(review);
    }

}
