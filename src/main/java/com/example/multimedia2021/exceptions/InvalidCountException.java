package com.example.multimedia2021.exceptions;

public class InvalidCountException extends HangmanException{

    public InvalidCountException() {
        super("Duplicate words found");
    }
}
