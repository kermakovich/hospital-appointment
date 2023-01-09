package solvd.laba.ermakovich.ha.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import solvd.laba.ermakovich.ha.domain.exception.IllegalOperationException;
import solvd.laba.ermakovich.ha.domain.exception.ResourceNotFoundException;
import solvd.laba.ermakovich.ha.domain.exception.EntityAlreadyExistsException;
import solvd.laba.ermakovich.ha.web.dto.ErrorResponseDto;
import solvd.laba.ermakovich.ha.web.dto.ErrorValidationResponseDto;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleEntityNotFoundException(RuntimeException ex) {
        return new ErrorResponseDto(ex.getMessage());
    }

    @ExceptionHandler({ IllegalOperationException.class, EntityAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleEntityAlreadyExistsException(RuntimeException ex) {
        return new ErrorResponseDto(ex.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorValidationResponseDto handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
         ex.getBindingResult().getAllErrors().forEach( error ->
                errors.add(((FieldError) error)
                            .getField() + " : " + error.getDefaultMessage()));
        return new ErrorValidationResponseDto(errors);
    }

//    @ExceptionHandler({RuntimeException.class})
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorResponseDto handleOtherException(RuntimeException ex) {
//        return new ErrorResponseDto(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
//    }

}
