package solvd.laba.ermakovich.ha.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientDto extends UserInfoDto {

    @Valid
    private AddressDto addressDto;

}
