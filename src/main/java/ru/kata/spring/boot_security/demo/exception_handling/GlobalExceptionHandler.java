package ru.kata.spring.boot_security.demo.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<IncorrectDataHandling> exceptionHandling(UserIdNotFoundException userIdNotFoundException) {
        IncorrectDataHandling incorrectDataHandling = new IncorrectDataHandling();
        incorrectDataHandling.setInfo(userIdNotFoundException.getMessage());
        return new ResponseEntity<>(incorrectDataHandling, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectDataHandling> exceptionHandling(Exception exception) {
        IncorrectDataHandling incorrectDataHandling = new IncorrectDataHandling();
        incorrectDataHandling.setInfo(exception.getMessage());
        return new ResponseEntity<>(incorrectDataHandling, HttpStatus.BAD_REQUEST);
    }
}
