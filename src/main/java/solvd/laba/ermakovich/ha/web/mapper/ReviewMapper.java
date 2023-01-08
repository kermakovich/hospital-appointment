package solvd.laba.ermakovich.ha.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import solvd.laba.ermakovich.ha.domain.Review;
import solvd.laba.ermakovich.ha.web.dto.ReviewDto;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PatientMapper.class, DoctorMapper.class})
public interface ReviewMapper {
    @Mapping(target = "doctor", source = "doctorDto")
    @Mapping(target = "patient", source = "patientDto")
    Review dtoToEntity(ReviewDto reviewDto);

    @Mapping(target = "doctorDto", source = "doctor")
    @Mapping(target = "patientDto", source = "patient")
    ReviewDto entityToDto(Review review);

    @Mapping(target = "doctorDto", source = "doctor")
    @Mapping(target = "patientDto", source = "patient")
    List<ReviewDto> entityToDto(List<Review> reviews);
}
