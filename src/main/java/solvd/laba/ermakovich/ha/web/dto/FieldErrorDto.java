package solvd.laba.ermakovich.ha.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "dto for errors on field")
public class FieldErrorDto extends ErrorDto {

    @Schema(description = "field name")
    private String fieldName;

    public FieldErrorDto(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }
}
