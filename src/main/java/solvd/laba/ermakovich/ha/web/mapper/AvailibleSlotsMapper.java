package solvd.laba.ermakovich.ha.web.mapper;

import org.mapstruct.Mapper;
import solvd.laba.ermakovich.ha.domain.doctor.AvailibleSlots;
import solvd.laba.ermakovich.ha.web.dto.AvailibleSlotsDto;

@Mapper(componentModel = "spring")
public interface AvailibleSlotsMapper {
    AvailibleSlotsDto entityToDto(AvailibleSlots availibleSlots);

}
