package solvd.laba.ermakovich.ha.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

import static solvd.laba.ermakovich.ha.web.security.SecurityConfig.SECURITY_SCHEME_NAME;

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
    @Operation(summary = "posts doctor`s review by patient")
    @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    public ReviewDto create(@RequestBody @Validated(onCreateReview.class) ReviewDto reviewDto) {
        Review review = reviewMapper.dtoToEntity(reviewDto);
        reviewService.create(review);
        return reviewMapper.entityToDto(review);
    }

    @PreAuthorize("(hasRole('PATIENT') or hasRole('ADMIN')) and hasAccessForReview(#reviewId)")
    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "deletes review by patient")
    @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    public void delete(@Parameter(description = "id review for delete") @PathVariable long reviewId) {
        reviewService.delete(reviewId);
    }

    @PreAuthorize("(hasRole('PATIENT') or hasRole('ADMIN')) and hasAccessForReview(#reviewId)")
    @PutMapping("/{reviewId}")
    @Operation(summary = "updates review by patient")
    @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    public ReviewDto update(@RequestBody @Valid ReviewDto reviewDto,
                            @Parameter(description = "id review for update") @PathVariable long reviewId) {
        Review review = reviewMapper.dtoToEntity(reviewDto);
        review = reviewService.update(reviewId, review);
        return reviewMapper.entityToDto(review);
    }

    @PreAuthorize("(hasRole('PATIENT') or hasRole('ADMIN')) and hasAccessForReview(#reviewId)")
    @GetMapping("/{reviewId}")
    @Operation(summary = "gets review by id")
    @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    public ReviewDto get(@Parameter(description = "id review") @PathVariable long reviewId) {
        Review review = reviewService.retrieveById(reviewId);
        return reviewMapper.entityToDto(review);
    }

}
