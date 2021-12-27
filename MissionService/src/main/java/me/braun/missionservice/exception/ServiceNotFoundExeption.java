package me.braun.missionservice.exception;


public class ServiceNotFoundExeption extends RuntimeException{
    public ServiceNotFoundExeption(){
        super("Service not found");
    }
}
