package com.wasim.buildbridge.exception;

public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException(String message){
        super(message);
    }
}
