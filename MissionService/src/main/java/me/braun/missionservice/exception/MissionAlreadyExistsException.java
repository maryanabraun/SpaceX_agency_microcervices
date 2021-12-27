package me.braun.missionservice.exception;


public class MissionAlreadyExistsException extends RuntimeException{
    public MissionAlreadyExistsException() {
        super("Mission already exists, enter another title");
    }
}
