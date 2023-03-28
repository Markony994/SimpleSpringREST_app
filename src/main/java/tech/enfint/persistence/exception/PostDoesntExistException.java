package tech.enfint.persistence.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Post with that uuid doesn't exist")
public class PostDoesntExistException extends Exception {

    public PostDoesntExistException(String message) {
        super(message);
    }
}
