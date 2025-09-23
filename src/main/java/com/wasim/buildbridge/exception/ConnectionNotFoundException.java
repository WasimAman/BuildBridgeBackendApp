package com.wasim.buildbridge.exception;

public class ConnectionNotFoundException extends RuntimeException{
    public ConnectionNotFoundException(String message){
        super(message);
    }
}
