package solvd.laba.ermakovich.ha.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AppointmentStatus {

    FUTURE("future"),
    DONE("done");

    private final String value;

}
