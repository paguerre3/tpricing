package com.capitol.pricing.domain.models.exceptions;

public class ItemNotFoundException extends Exception{
    public ItemNotFoundException(String message){
        super(message);
    }
}
