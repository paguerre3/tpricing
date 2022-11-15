package com.capitol.pricing.exceptions;

public class MissingArgumentException extends Exception{
    public MissingArgumentException(String message){
        super(message);
    }
}