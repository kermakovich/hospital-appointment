package solvd.laba.ermakovich.ha.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDto save(@RequestBody @Validated(onCreateReview.class) ReviewDto reviewDto) {
        Review review = reviewMapper.dtoToEntity(reviewDto);
        reviewService.save(review);
        return reviewMapper.entityToDto(review);
    }

    @GetMapping("/doctors/{doctorId}/reviews")
    public List<ReviewDto> getByDoctor(@PathVariable long doctorId) {
        List<Review> reviews = reviewService.getAllByDoctorId(doctorId);
        return reviewMapper.entityToDto(reviews);
    }

    @DeleteMapping("/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long reviewId) {
        reviewService.delete(reviewId);
    }

    @PutMapping("/reviews/{reviewId}")
    public ReviewDto update(@RequestBody @Valid ReviewDto reviewDto, @PathVariable long reviewId) {
        Review review = reviewMapper.dtoToEntity(reviewDto);
        review = reviewService.update(reviewId, review);
        return reviewMapper.entityToDto(review);
    }

    @GetMapping("/reviews/{reviewId}")
    public ReviewDto getOne(@PathVariable long reviewId) {
        Review review = reviewService.getById(reviewId);
        return reviewMapper.entityToDto(review);
    }

}
