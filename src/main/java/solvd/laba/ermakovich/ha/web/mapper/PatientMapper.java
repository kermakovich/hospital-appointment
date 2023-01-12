package solvd.laba.ermakovich.ha.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import solvd.laba.ermakovich.ha.domain.Patient;
import solvd.laba.ermakovich.ha.web.dto.PatientDto;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "addressDto", source = "address")
    PatientDto entityToDto(Patient patient);

    @Mapping(target = "address", source = "addressDto")
    Patient dtoToEntity(PatientDto patientDto);

}
