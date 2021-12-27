package me.braun.spacecraftservice.exception;

public class SpacecraftAlreadyExistsException extends RuntimeException{
    public SpacecraftAlreadyExistsException(){
        super("Spacecraft already exists");
    }
}
