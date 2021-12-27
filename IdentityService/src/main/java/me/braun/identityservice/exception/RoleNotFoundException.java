package me.braun.identityservice.exception;


public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(){
        super("Role not found");
    }
}
