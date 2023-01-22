package solvd.laba.ermakovich.ha.web.mapper.jwt;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import solvd.laba.ermakovich.ha.domain.jwt.JwtResponse;
import solvd.laba.ermakovich.ha.web.dto.jwt.JwtResponseDto;
import solvd.laba.ermakovich.ha.web.mapper.jwt.JwtAccessMapper;

@Mapper(componentModel = "spring", uses = JwtAccessMapper.class)
public interface JwtResponseMapper {

    @Mapping(target = "accessDto", source = "access")
    JwtResponseDto entityToDto(JwtResponse response);

}
