package solvd.laba.ermakovich.ha.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import solvd.laba.ermakovich.ha.domain.PatientCard;
import solvd.laba.ermakovich.ha.web.dto.PatientCardDto;

@Mapper(componentModel = "spring", uses = PatientMapper.class)
public interface PatientCardMapper {

    @Mapping(target = "patientDto", source = "patient")
    PatientCardDto entityToDto(PatientCard patientCard);

    @Mapping(target = "patient", source = "patientDto")
    PatientCard dtoToEntity(PatientCardDto patientCardDto);

}
