package solvd.laba.ermakovich.ha.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patient extends UserInfo implements Cloneable {

    private Address address;

    @Override
    public Patient clone() {
        try {
            Patient clone = (Patient) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
