package solvd.laba.ermakovich.ha.domain.hospital;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Department {

    SURGERY("surgery"),
    THERAPEUTIC("therapeutic"),
    DENTAL("dental");

    private final String value;

    @Override
    public String toString() {
        return value.toUpperCase();
    }

}
