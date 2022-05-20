package com.example.multimedia2021.exceptions;

public class InvalidRangeException extends HangmanException{
    public InvalidRangeException() {
        super("Found word with less than 6 letters");
    }
}
