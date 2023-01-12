package solvd.laba.ermakovich.ha.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorDto(String fieldName, String message) {

    public ErrorDto(String message) {
        this(null, message);
    }

}
