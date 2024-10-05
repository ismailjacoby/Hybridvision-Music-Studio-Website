package be.technobel.makerhub_backend.pl.validators;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles validation errors across the application, providing a detailed map of field error messages.
 */
@RestControllerAdvice
public class ValidationHandler {

    /**
     * Captures and processes {@link MethodArgumentNotValidException} thrown when validation on an argument annotated with {@code @Valid} fails.
     * @param e The exception object containing validation error details.
     * @return A map of field names to validation error messages.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException e){
        Map<String,String> errors = new HashMap<>();
        List<ObjectError> allErrors= e.getBindingResult().getAllErrors();
        allErrors.forEach(err ->{
            FieldError fe = (FieldError) err;
            errors.put(fe.getField(), fe.getDefaultMessage());
        });
        return errors;
    }
}
