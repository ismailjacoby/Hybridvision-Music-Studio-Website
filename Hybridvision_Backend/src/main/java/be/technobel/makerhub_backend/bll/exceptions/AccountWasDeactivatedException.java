package be.technobel.makerhub_backend.bll.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when accessing functionality with a deactivated account.
 * It results in an HTTP 401 Unauthorized response, advising the user to contact support.
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Your account was deactivated. Please contact support to reactivate your account.")
public class AccountWasDeactivatedException extends RuntimeException{
    /**
     * Constructs a new exception with a detailed message.
     * @param message the detail message.
     */
    public AccountWasDeactivatedException(String message){
        super(message);
    }
}