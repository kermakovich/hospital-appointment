package solvd.laba.ermakovich.ha.web.mapper.jwt;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import solvd.laba.ermakovich.ha.domain.jwt.JwtResponse;
import solvd.laba.ermakovich.ha.web.dto.jwt.JwtResponseDto;

@Mapper(componentModel = "spring", uses = {JwtAccessMapper.class, RefreshMapper.class})
public interface JwtResponseMapper {

    @Mapping(target = "accessDto", source = "access")
    @Mapping(target = "refreshDto", source = "refresh")
    JwtResponseDto entityToDto(JwtResponse response);

}
