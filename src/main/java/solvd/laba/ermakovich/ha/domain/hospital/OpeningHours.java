package solvd.laba.ermakovich.ha.domain.hospital;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalTime;

@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "hospital.hours")
public class OpeningHours {

    public final LocalTime start;
    public final LocalTime finish;
    public final Integer minutesRange;

    public boolean isWithinOpenHours(LocalTime time) {
            return (time.isAfter(start) || time.equals(start)) && time.isBefore(finish);
    }

}
