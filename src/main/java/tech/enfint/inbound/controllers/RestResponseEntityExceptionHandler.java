package tech.enfint.inbound.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.enfint.dto.ErrorDto;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler({WebExchangeBindException.class})
    protected ResponseEntity<ErrorDto> handleBindException(Throwable throwable, WebRequest request)
    {
        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST,
                throwable.getMessage(),
                "There was an error, bad format of input.");

        return new ResponseEntity<>(errorDto, new HttpHeaders(), errorDto.getStatus());
    }

    @ExceptionHandler()
    protected ResponseEntity<ErrorDto> handle(Throwable throwable, WebRequest request)
    {
        ErrorDto errorDto = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR,
                throwable.getMessage(),
                "There was an error, please try again, or submit a ticket.");

        return new ResponseEntity<>(errorDto, new HttpHeaders(), errorDto.getStatus());
    }



}
