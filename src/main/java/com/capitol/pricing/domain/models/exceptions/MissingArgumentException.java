package com.capitol.pricing.domain.models.exceptions;

public class MissingArgumentException extends Exception{
    public MissingArgumentException(String message){
        super(message);
    }
}