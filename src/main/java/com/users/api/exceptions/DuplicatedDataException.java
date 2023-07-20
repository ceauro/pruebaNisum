package com.users.api.exceptions;

public class DuplicatedDataException extends Exception{

    public DuplicatedDataException(String message){
        super(message);
    }
}
