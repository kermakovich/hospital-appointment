package solvd.laba.ermakovich.ha.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ErrorValidationResponseDto {

    private List<String> errors;
}
