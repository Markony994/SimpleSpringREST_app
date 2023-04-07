package tech.enfint.persistence.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PostException extends Exception {

    public PostException(String message) {
        super(message);
    }
}
