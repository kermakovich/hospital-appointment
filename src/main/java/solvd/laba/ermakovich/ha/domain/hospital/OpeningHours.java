package solvd.laba.ermakovich.ha.domain.hospital;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalTime;

@Data
@ConfigurationProperties(prefix = "hospital.hours")
public class OpeningHours {

    public final LocalTime start;
    public final LocalTime finish;
    public final long minutesRange;

    public boolean isWithinOpenHours(LocalTime time) {
            return (time.isAfter(start) || time.equals(start)) && time.isBefore(finish);
    }

}
