package solvd.laba.ermakovich.ha.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "patient info")
public class PatientDto extends UserInfoDto {

    @Valid
    private AddressDto addressDto;

}
