package solvd.laba.ermakovich.ha.web.mapper;

import org.mapstruct.Mapper;
import solvd.laba.ermakovich.ha.domain.doctor.AvailableSlots;
import solvd.laba.ermakovich.ha.web.dto.AvailableSlotsDto;

@Mapper(componentModel = "spring")
public interface AvailableSlotsMapper {
    
    AvailableSlotsDto entityToDto(AvailableSlots availableSlots);

}
