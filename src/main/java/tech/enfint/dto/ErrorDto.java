package tech.enfint.dto;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ErrorDto
{
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ErrorDto(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ErrorDto(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}
