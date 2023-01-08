package solvd.laba.ermakovich.ha.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
@Data
public class ErrorValidationResponseDto {

    private Map<String, String> errors;
    private int statusCode;
}
