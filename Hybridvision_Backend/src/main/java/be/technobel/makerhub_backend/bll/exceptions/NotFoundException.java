package be.technobel.makerhub_backend.bll.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for indicating that a requested resource was not found.
 * Triggers an HTTP 404 Not Found response when thrown.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    /**
     * Constructs a new NotFoundException with a detailed message about the missing resource.
     * @param message Explanation of what resource was not found.
     */
    public NotFoundException(String message){
        super(message);
    }
}
