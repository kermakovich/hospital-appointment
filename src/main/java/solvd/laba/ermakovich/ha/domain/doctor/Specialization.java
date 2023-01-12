package solvd.laba.ermakovich.ha.domain.doctor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Specialization {

    DENTIST("dentist"),
    THERAPIST("therapist"),
    ENDOCRINOLOGIST("endocrinologist"),
    PHYSIOTHERAPIST("physiotherapist");

    private final String value;

    @Override
    public String toString() {
        return value;
    }

}
