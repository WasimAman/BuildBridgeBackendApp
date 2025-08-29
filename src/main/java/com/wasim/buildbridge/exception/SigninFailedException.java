package com.wasim.buildbridge.exception;

public class SigninFailedException extends RuntimeException{
    public SigninFailedException(String message){
        super(message);
    }
}
