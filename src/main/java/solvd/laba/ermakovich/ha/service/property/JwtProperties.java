package solvd.laba.ermakovich.ha.service.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    private final long access;
    private final long refresh;
    private final String secret;

}