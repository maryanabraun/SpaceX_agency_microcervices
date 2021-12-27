package me.braun.missionservice.exception;

public class StatusNotFoundException extends RuntimeException {
    public StatusNotFoundException(){
        super("Status not found");
    }
}
