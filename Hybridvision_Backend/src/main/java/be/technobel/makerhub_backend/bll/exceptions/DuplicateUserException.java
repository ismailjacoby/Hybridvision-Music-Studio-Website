package be.technobel.makerhub_backend.bll.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicating an attempt to create a user that already exists.
 * Results in an HTTP 400 Bad Request response when thrown.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateUserException extends RuntimeException{
    /**
     * Constructs this exception with a message explaining the duplicate user issue.
     * @param message Detail about the duplicate user scenario.
     */
    public DuplicateUserException(String message){
        super(message);
    }
}
