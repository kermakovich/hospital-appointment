package solvd.laba.ermakovich.ha.web.dto;

public record ErrorDto(String fieldName, String message) {

    public ErrorDto(String message) {
        this(null, message);
    }

}
