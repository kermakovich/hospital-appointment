package solvd.laba.ermakovich.ha.web.mapper.jwt;

import org.mapstruct.Mapper;
import solvd.laba.ermakovich.ha.domain.jwt.Authentication;
import solvd.laba.ermakovich.ha.web.dto.jwt.AuthenticationDto;

@Mapper(componentModel = "spring")
public interface AuthenticationMapper {

    Authentication dtoToEntity(AuthenticationDto authenticationDto);

}
