package solvd.laba.ermakovich.ha.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {

    private Long id;

    @NotNull(message = "can`t be null")
    @NotBlank(message = "can`t be empty")
    @Size(min = 1, max = 100, message = "can`t be empty")
    private String city;

    @NotNull(message = "can`t be null")
    @NotBlank(message = "can`t be empty")
    @Size(min = 1, max = 150, message = "can`t be empty")
    private String street;

    @NotNull(message = "can`t be null")
    @Min(value = 1, message = "can`t be negative or 0")
    private Integer house;

    @NotNull(message = "can`t be null")
    @Min(value = 1,  message = "can`t be negative or 0")
    private Integer flat;
}
