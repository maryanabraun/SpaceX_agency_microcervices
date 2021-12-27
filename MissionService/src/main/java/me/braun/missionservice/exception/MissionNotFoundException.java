package me.braun.missionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "role not found")
public class MissionNotFoundException extends RuntimeException{
    public MissionNotFoundException(){
        super("Mission not found");
    }
}
