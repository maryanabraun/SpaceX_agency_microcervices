package me.braun.spacecraftservice.exception;


public class CraftNotFoundException extends RuntimeException{
    public CraftNotFoundException(){
        super("Spacecraft not found");
    }
}
