package solvd.laba.ermakovich.ha.web.dto;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDto extends UserInfoDto {

    @Valid
    private AddressDto addressDto;

}
