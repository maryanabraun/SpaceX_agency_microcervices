package me.braun.authentityservice.exception;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException() {
        super("User already exists");
}
}