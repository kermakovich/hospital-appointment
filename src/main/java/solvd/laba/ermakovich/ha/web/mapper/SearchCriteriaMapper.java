package solvd.laba.ermakovich.ha.web.mapper;

import org.mapstruct.Mapper;
import solvd.laba.ermakovich.ha.domain.SearchDoctorCriteria;
import solvd.laba.ermakovich.ha.web.dto.SearchCriteriaDto;

@Mapper(componentModel = "spring")
public interface SearchCriteriaMapper {

    SearchDoctorCriteria dtoToEntity(SearchCriteriaDto searchCriteriaDto);

    SearchCriteriaDto entityToDto(SearchDoctorCriteria searchDoctorCriteria);

}
