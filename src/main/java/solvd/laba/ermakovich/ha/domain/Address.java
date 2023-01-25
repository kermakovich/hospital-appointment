package solvd.laba.ermakovich.ha.domain;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private Long id;
    private String city;
    private String street;
    private Integer house;
    private Integer flat;

    public Address(String city, String street, Integer house, Integer flat) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
    }
}
