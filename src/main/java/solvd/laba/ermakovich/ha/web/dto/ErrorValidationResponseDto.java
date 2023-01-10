package solvd.laba.ermakovich.ha.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ErrorValidationResponseDto {

    private List<String> errors;
}
