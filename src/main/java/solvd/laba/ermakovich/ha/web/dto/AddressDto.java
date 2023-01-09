package solvd.laba.ermakovich.ha.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressDto {

    private Long id;
    @NotNull(message = "city can`t be null")
    @Size(min = 1, max = 100, message = "city can`t be empty")
    private String city;
    @NotNull(message = "street can`t be null")
    @Size(min = 1, max = 150, message = "city can`t be empty")
    private String street;
    @NotNull(message = "house can`t be null")
    @Min(value = 1, message = "house can`t be negative or 0")
    private Integer house;
    @NotNull(message = "flat can`t be null")
    @Min(value = 1,  message = "flat can`t be negative or 0")
    private Integer flat;
}
