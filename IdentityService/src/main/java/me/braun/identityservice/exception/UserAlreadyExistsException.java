package me.braun.identityservice.exception;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException() {
        super("User already exists");
}
}