package solvd.laba.ermakovich.ha.web.mapper;

import org.mapstruct.Mapper;
import solvd.laba.ermakovich.ha.domain.SearchCriteria;
import solvd.laba.ermakovich.ha.web.dto.SearchCriteriaDto;

@Mapper(componentModel = "spring")
public interface SearchCriteriaMapper {

    SearchCriteria dtoToEntity(SearchCriteriaDto searchCriteriaDto);

    SearchCriteriaDto entityToDto(SearchCriteria searchCriteria);

}
