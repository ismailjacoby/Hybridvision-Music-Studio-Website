package be.technobel.makerhub_backend.bll.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Exception thrown when an attempt is made to use a blocked account. This triggers an HTTP 401 Unauthorized response.
 * Use this exception to enforce account access policies and inform users of their account status.
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Your Account is blocked. Please contact support to reactivate your account.")
public class AccountBlockedException extends RuntimeException{

    /**
     * Constructs an AccountBlockedException with a detailed message.
     *
     * @param message Explanation of why the account is blocked.
     */
    public AccountBlockedException(String message){
        super(message);
    }
}
