package ru.kata.spring.boot_security.demo.exception_handling;

public class UserIdNotFoundException extends RuntimeException {
    public UserIdNotFoundException(String message) {
        super(message);
    }
}
