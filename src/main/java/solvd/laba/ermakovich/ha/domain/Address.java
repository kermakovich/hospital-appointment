package solvd.laba.ermakovich.ha.domain;

import lombok.*;


@Setter
@Getter
public class Address {

    private Long id;
    private String city;
    private String street;
    private Integer house;
    private Integer flat;

}
