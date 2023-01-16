package solvd.laba.ermakovich.ha.web.controller;

import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import solvd.laba.ermakovich.ha.domain.Patient;
import solvd.laba.ermakovich.ha.domain.Review;
import solvd.laba.ermakovich.ha.repository.DoctorRepository;
import solvd.laba.ermakovich.ha.repository.ReviewRepository;
import solvd.laba.ermakovich.ha.web.dto.PatientDto;
import solvd.laba.ermakovich.ha.web.dto.group.onCreate;
import solvd.laba.ermakovich.ha.web.mapper.PatientMapper;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TsetController {
    private final DoctorRepository userInfoMyBatisMapper;
    private final ReviewRepository reviewRepository;
    private final PatientMapper patientMapper;

    @PostMapping("test/user")
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<Review> create(@Validated({onCreate.class, Default.class}) @RequestBody PatientDto patientDto) {
        Patient patient = patientMapper.dtoToEntity(patientDto);
        Optional<Review> doctor = reviewRepository.findById(3L);
        if (doctor.isPresent()) {
            return reviewRepository.findById(16L);
        }
        return null;
    }
}
