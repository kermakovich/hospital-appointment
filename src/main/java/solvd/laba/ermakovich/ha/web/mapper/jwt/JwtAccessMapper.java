package solvd.laba.ermakovich.ha.web.mapper.jwt;

import org.mapstruct.Mapper;
import solvd.laba.ermakovich.ha.domain.jwt.JwtAccess;
import solvd.laba.ermakovich.ha.web.dto.jwt.JwtAccessDto;

@Mapper(componentModel = "spring")
public interface JwtAccessMapper {

    JwtAccessDto entityToDto(JwtAccess access);

}
