package solvd.laba.ermakovich.ha.web.mapper;

import org.mapstruct.Mapper;
import solvd.laba.ermakovich.ha.domain.SearchAppointmentCriteria;
import solvd.laba.ermakovich.ha.web.dto.SearchAppointmentCriteriaDto;

@Mapper(componentModel = "spring")
public interface SearchAppointmentCriteriaMapper {

    SearchAppointmentCriteria dtoToEntity(SearchAppointmentCriteriaDto criteriaDto);

    SearchAppointmentCriteriaDto entityToDto(SearchAppointmentCriteria searchDoctorCriteria);

}
