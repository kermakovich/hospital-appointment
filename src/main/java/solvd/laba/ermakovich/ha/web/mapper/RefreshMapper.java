package solvd.laba.ermakovich.ha.web.mapper;

import org.mapstruct.Mapper;
import solvd.laba.ermakovich.ha.domain.jwt.Refresh;
import solvd.laba.ermakovich.ha.web.dto.jwt.RefreshDto;

@Mapper(componentModel = "spring")
public interface RefreshMapper {

    Refresh dtoToEntity(RefreshDto refreshDto);

}
