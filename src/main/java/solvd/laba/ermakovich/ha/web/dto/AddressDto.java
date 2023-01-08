package solvd.laba.ermakovich.ha.web.dto;

import lombok.Data;

@Data
public class AddressDto {

    private Long id;
    private String city;
    private String street;
    private Integer house;
    private Integer flat;
}
