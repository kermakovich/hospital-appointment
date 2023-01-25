package solvd.laba.ermakovich.ha.domain;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class UserInfo implements Cloneable{

    private Long id;
    private String name;
    private String surname;
    private String fatherhood;
    private LocalDate birthday;
    private String email;
    private String password;
    private UserRole role;

}

