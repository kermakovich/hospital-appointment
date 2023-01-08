package solvd.laba.ermakovich.ha.domain;

import lombok.*;

import java.time.LocalDate;

@Data
public class UserInfo {

    private Long id;
    private String name;
    private String surname;
    private String fatherhood;
    private LocalDate birthday;
    private String email;
    private String password;

}

