package solvd.laba.ermakovich.ha.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;
import solvd.laba.ermakovich.ha.web.dto.DoctorDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    Doctor dtoToEntity(DoctorDto doctorDto);

    @Mapping(target = "password", ignore = true)
    DoctorDto entityToDto(Doctor doctor);

    @Mapping(target = "password", ignore = true)
    List<DoctorDto> entityToDto(List<Doctor> doctors);

}
