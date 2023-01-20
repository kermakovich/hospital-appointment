package solvd.laba.ermakovich.ha.web.mapper;

import org.mapstruct.Mapper;
import solvd.laba.ermakovich.ha.domain.jwt.JwtResponse;
import solvd.laba.ermakovich.ha.web.dto.jwt.JwtResponseDto;

@Mapper(componentModel = "spring")
public interface JwtResponseMapper {

    JwtResponseDto entityToDto(JwtResponse response);

}
