package solvd.laba.ermakovich.ha.domain;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "externalId")
public class UserInfo implements Cloneable {

    private Long id;
    private UUID externalId;
    private String name;
    private String surname;
    private String fatherhood;
    private LocalDate birthday;
    private String email;
    private String password;
    private UserRole role;

}

