package be.technobel.makerhub_backend.pl.controllers;

import be.technobel.makerhub_backend.bll.exceptions.AccountBlockedException;
import be.technobel.makerhub_backend.bll.exceptions.AccountWasDeactivatedException;
import be.technobel.makerhub_backend.bll.exceptions.DuplicateUserException;
import be.technobel.makerhub_backend.bll.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(AccountBlockedException.class)
    public ResponseEntity<Error> handleAccountBlockedException(AccountBlockedException ex, HttpServletRequest req){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Error(ex.getMessage()));
    }

    @ExceptionHandler(AccountWasDeactivatedException.class)
    public ResponseEntity<Error> handleAccountWasDeactivatedException(AccountWasDeactivatedException ex, HttpServletRequest req){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Error(ex.getMessage()));
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Error> handleDuplicateUserException(DuplicateUserException ex, HttpServletRequest req){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Error> handleNotFoundException(NotFoundException ex, HttpServletRequest req){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error(ex.getMessage()));
    }

}
