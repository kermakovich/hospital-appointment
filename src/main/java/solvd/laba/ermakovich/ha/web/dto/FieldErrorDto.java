package solvd.laba.ermakovich.ha.web.dto;

import lombok.Getter;

@Getter
public class FieldErrorDto extends ErrorDto {

    private String fieldName;

    public FieldErrorDto(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }
}
