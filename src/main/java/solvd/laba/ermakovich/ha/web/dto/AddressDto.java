package solvd.laba.ermakovich.ha.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Address info")
public class AddressDto {

    @Schema(description = "address id")
    private Long id;

    @NotBlank(message = "can`t be empty")
    @Size(min = 1, max = 100, message = "can`t be empty")
    @Schema(description = "city")
    private String city;

    @NotBlank(message = "can`t be empty")
    @Size(min = 1, max = 150, message = "can`t be empty")
    @Schema(description = "street")
    private String street;

    @NotNull(message = "can`t be null")
    @Min(value = 1, message = "can`t be negative or 0")
    @Schema(description = "house")
    private Integer house;

    @NotNull(message = "can`t be null")
    @Min(value = 1,  message = "can`t be negative or 0")
    @Schema(description = "flat")
    private Integer flat;
}
