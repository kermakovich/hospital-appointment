package solvd.laba.ermakovich.ha.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import solvd.laba.ermakovich.ha.domain.exception.*;
import solvd.laba.ermakovich.ha.web.dto.ErrorResponseDto;
import solvd.laba.ermakovich.ha.web.dto.ErrorValidationResponseDto;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AppointmentNotFoundException.class, DoctorNotFoundException.class,
                        ReviewNotFoundException.class, PatientNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleEntityNotFoundException(RuntimeException ex) {
        return new ErrorResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler({EmailAlreadyExistsException.class, AppointmentIsBusyException.class,
            WrongAppointmentTimeException.class, PatientNotHaveDoneAppointmentException.class,
            ReviewAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleEntityAlreadyExistsException(RuntimeException ex) {
        return new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorValidationResponseDto handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
         ex.getBindingResult().getAllErrors().forEach( error ->
                errors.put(((FieldError) error).getField(), error.getDefaultMessage())
        );
        return new ErrorValidationResponseDto(errors, HttpStatus.BAD_REQUEST.value());
    }

//    @ExceptionHandler({RuntimeException.class})
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorResponseDto handleOtherException(RuntimeException ex) {
//        return new ErrorResponseDto(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
//    }

}
