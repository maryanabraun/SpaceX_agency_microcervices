package me.braun.authentityservice.api.controller;


import lombok.extern.slf4j.Slf4j;
import me.braun.authentityservice.api.dto.ErrorDto;
import me.braun.authentityservice.exception.RoleNotFoundException;
import me.braun.authentityservice.exception.UserAlreadyExistsException;
import me.braun.authentityservice.exception.UserNotFoundException;


import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class HandlerController {

    private final MessageSource messageSource;

    public HandlerController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            RoleNotFoundException.class
    })
    public @NotNull ResponseEntity<ErrorDto> handleNotFoundException(@NotNull RuntimeException e,
                                                                     WebRequest request) {
        ErrorDto error = ErrorDto.builder()
                .code("404 NOT FOUND")
                .description(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler({
            UserAlreadyExistsException.class,

    })
    public @NotNull ResponseEntity<ErrorDto> handleBadRequestException(@NotNull RuntimeException e,
                                                                       WebRequest request) {
        ErrorDto error = ErrorDto.builder()
                .code("400 BAD REQUEST")
                .description(e.getMessage())
                .build();
        return ResponseEntity.badRequest().body(error);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ErrorDto>> validationExceptionHandler(ConstraintViolationException ex, WebRequest request) {
        log.info("ex:", ex.getConstraintViolations().toArray());
        List<ErrorDto> errorDtos = ex.getConstraintViolations().stream().map(violation ->
                ErrorDto.builder().description(violation.getPropertyPath() + " invalid. " +
                        messageSource.getMessage(violation.getMessage(), null, request.getLocale()))
                        .code("Bad Request").build()
        ).collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errorDtos);
    }
}
