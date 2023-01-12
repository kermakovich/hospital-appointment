package solvd.laba.ermakovich.ha.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.web.dto.AppointmentDto;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {PatientCardMapper.class, DoctorMapper.class})
public interface AppointmentMapper {

    @Mapping(target = "doctor", source = "doctorDto")
    @Mapping(target = "patientCard", source = "patientCardDto")
    Appointment dtoToEntity(AppointmentDto appointmentDto);

    @Mapping(target = "doctorDto", source = "doctor")
    @Mapping(target = "patientCardDto", source = "patientCard")
    AppointmentDto entityToDto(Appointment appointment);

    @Mapping(target = "doctorDto", source = "doctor")
    @Mapping(target = "patientCardDto", source = "patientCard")
    List<AppointmentDto> entityToDto(List<Appointment> appointment);

}
